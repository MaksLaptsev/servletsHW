package repository.impl;

import entity.User;
import repository.UserRepository;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl<T> implements UserRepository<User> {
    private Map<Long,User> users = new HashMap<>();
    private static Long id = 0L;

    public UserRepositoryImpl() {
        users = (Map<Long, User>) JsonUtil.getMapOfObjects("JsonPersonsList.json", (Class<? extends Map<?, ?>>) users.getClass(),Long.class,User.class);
        id = (long) users.size();
    }

    @Override
    public Optional<User>  create(User user) {
        user.setId(++id);
        users.put(id, user);
        return Optional.of(users.get(id));
    }

    @Override
    public Optional<User> update(User user) {
        users.put(user.getId() ,user);
        return Optional.of(user);
    }

    @Override
    public Optional<User>  getById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public boolean deleteById(Long id) {
        return users.remove(id) != null;
    }

    @Override
    public Optional<User> findByLogin(String s) {
        return users.values().stream().filter(u->u.getLogin().equals(s)).findFirst();
    }
}
