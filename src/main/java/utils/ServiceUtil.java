package utils;

import service.RoleService;
import service.UserService;
import service.impl.RoleServiceImpl;
import service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class ServiceUtil {
    private static Map<Class<?>,Object> cacheMap = new HashMap<>();
    static {
        cacheMap.put(UserService.class, new UserServiceImpl());
        cacheMap.put(RoleService.class, new RoleServiceImpl());
    }
    public static Object getService(Class<?> clasz){
        return cacheMap.get(clasz);
    }
}
