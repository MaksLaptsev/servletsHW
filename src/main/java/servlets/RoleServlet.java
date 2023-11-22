package servlets;

import entity.Role;
import exception.RoleException;
import service.RoleService;
import utils.JsonUtil;
import utils.ServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/role")
public class RoleServlet extends HttpServlet {
    private RoleService<Role> roleService;

    public void init() {
        this.roleService = (RoleService<Role> ) ServiceUtil.getService(RoleService.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        try{
            Role role = roleService.getById(id);
            sendResp(resp, role, 200);
        }catch (RoleException e){
            sendExceptResp(resp,e.getMessage(),404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Role role = getFromRequest(req);
        try{
            Role role1 = roleService.create(role);
            sendResp(resp, role1, 201);
        }catch (RoleException e){
            sendExceptResp(resp,e.getMessage(),304);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Role role = getFromRequest(req);
        try{
            Role role1 = roleService.update(role);
            sendResp(resp, role1, 201);
        }catch (RoleException e){
            sendExceptResp(resp,e.getMessage(),304);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        boolean b = roleService.deleteById(id);
        sendResp(resp, b, 200);
    }

    private void sendResp(HttpServletResponse response, Object o, int code) throws IOException {
        String role1 = JsonUtil.objectToJson(o);
        response.getWriter().write(role1);
        response.setStatus(code);
        response.setContentType("application/json");
    }

    private Role getFromRequest(HttpServletRequest request) throws IOException {
        String res = request.getAttribute("body").toString();
        return (Role) JsonUtil.jsonToObject(res,Role.class);
    }

    private void sendExceptResp(HttpServletResponse response,String mes,int code) throws IOException {
        response.getWriter().write(mes);
        response.setStatus(code);
        response.setContentType("text/plain");
    }
}
