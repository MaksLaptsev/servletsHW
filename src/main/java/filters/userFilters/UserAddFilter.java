package filters.userFilters;

import entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebFilter(value = "/user", filterName = "2")
public class UserAddFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if(req.getMethod().equalsIgnoreCase("post")){
            Set<Role> roles = (Set<Role>) req.getSession().getAttribute("roles");

            if (roles.stream().noneMatch(arr -> arr.getRole().equals("ADMIN"))) {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/plain");
                response.getWriter().write("Для совершения операции у пользователя недостаточно прав");
            }else chain.doFilter(request, response);
        }else chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
