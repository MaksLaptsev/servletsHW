package servlets;
import entity.User;
import exception.UserException;
import service.UserService;
import utils.JsonUtil;
import utils.ServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService<User> userService;

    @Override
    public void init() {
        this.userService = (UserService<User>) ServiceUtil.getService(UserService.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        try{
            User user = userService.getById(id);
            sendResp(resp, user, 200);
        }catch (UserException e){
            sendExceptResp(resp,e.getMessage(),404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = getFromRequest(req);
        try{
            User user1 = userService.create(user);
            sendResp(resp, user1, 201);
        }catch (UserException e){
            sendExceptResp(resp,e.getMessage(),304);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = getFromRequest(req);
        try{
            User user1 = userService.update(user);
            sendResp(resp, user1, 201);
        }catch (UserException e){
            sendExceptResp(resp,e.getMessage(),304);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        User activeUser = (User) req.getSession().getAttribute("activeUser");
        boolean b = userService.deleteById(id);
        if (Objects.equals(activeUser.getId(), id)){
           req.getSession().invalidate();
        }
        sendResp(resp, b, 200);
    }

    private void sendResp(HttpServletResponse response, Object o, int code) throws IOException {
        String user1 = JsonUtil.objectToJson(o);
        response.getWriter().write(user1);
        response.setStatus(code);
        response.setContentType("application/json");
    }

    private User getFromRequest(HttpServletRequest request) throws IOException {
        String res = request.getAttribute("body").toString();
        return (User) JsonUtil.jsonToObject(res,User.class);
    }

    private void sendExceptResp(HttpServletResponse response,String mes,int code) throws IOException {
        response.getWriter().write(mes);
        response.setStatus(code);
        response.setContentType("text/plain");
    }
}
