package filters.roleFilters;

import entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@WebFilter(value = "/role", filterName = "5")
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Set<Role> roles = (Set<Role>) req.getSession().getAttribute("roles");
        if (req.getMethod().equalsIgnoreCase("get")){
            chain.doFilter(request, response);
        }else if (roles.stream().noneMatch(arr -> arr.getRole().equals("ADMIN"))){
            throw new ServletException("Для совершения операции у пользователя недостаточно прав");
        }else chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
