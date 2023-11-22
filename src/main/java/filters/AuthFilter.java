package filters;

import entity.User;
import exception.UserException;
import service.UserService;
import utils.ServiceUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebFilter(urlPatterns = {"/user","/role"}, filterName = "0")
public class AuthFilter implements Filter {
    private UserService<User> userService;

    @Override
    public void init(FilterConfig filterConfig) {
        this.userService = (UserService<User>) ServiceUtil.getService(UserService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization").split(" ")[1];
        Base64.Decoder decoder = Base64.getDecoder();

        String[] data = new String(decoder.decode(authorization)).split(":");

        try{
            User user = userService.findByLogin(data[0]);

            if (user.getPassword().equals(data[1])) {
                req.getSession().setAttribute("roles", user.getRole());
                req.getSession().setAttribute("activeUser",user);
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }catch (UserException e){
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("text/plain");
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
