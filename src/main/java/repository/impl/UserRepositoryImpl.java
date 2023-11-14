package repository.impl;

import entity.User;
import repository.UserRepository;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl<T> implements UserRepository<User> {
    private Map<Long,User> users = new HashMap<>();
    private static Long id = 0L;

    public UserRepositoryImpl() {
        users = (Map<Long, User>) JsonUtil.getMapOfObjects("JsonPersonsList.json", (Class<? extends Map<?, ?>>) users.getClass(),Long.class,User.class);
        id = (long) users.size();
    }

    @Override
    public User create(User user) {
        user.setId(++id);
        users.put(id, user);
        return users.get(id);
    }

    @Override
    public User update(User user) {
        users.put(user.getId() ,user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return users.get(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return users.remove(id) != null;
    }

    @Override
    public User findByLogin(String s) {
        return users.values().stream().filter(u->u.getLogin().equals(s)).findFirst().orElseThrow();
    }
}
