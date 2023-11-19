package filters.userFilters;

import entity.Role;
import entity.User;
import utils.JsonUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@WebFilter(value = "/user", filterName = "3")
public class UserUpdateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equalsIgnoreCase("put")) {
            Set<Role> roles = (Set<Role>) req.getSession().getAttribute("roles");

            User userUpd = (User) JsonUtil.jsonToObject(request.getAttribute("body").toString(),User.class);
            User activeUser = (User) req.getSession().getAttribute("activeUser");

            if (Objects.equals(userUpd.getId(), activeUser.getId()) && userUpd.getRole()==null){
                chain.doFilter(request, response);
            }else if (roles.stream().anyMatch(arr -> arr.getRole().equals("ADMIN"))) {
                if(Objects.equals(userUpd.getId(), activeUser.getId()) && userUpd.getRole()!=null){
                    req.getSession().setAttribute("roles",userUpd.getRole());
                    chain.doFilter(request, response);
                }else chain.doFilter(request, response);
            }else throw new ServletException("Для совершения операции у пользователя недостаточно прав");
        }else chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
