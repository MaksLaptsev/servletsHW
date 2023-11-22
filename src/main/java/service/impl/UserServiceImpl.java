package service.impl;

import entity.User;
import exception.UserException;
import lombok.SneakyThrows;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.lang.reflect.Field;

public class UserServiceImpl implements UserService<User> {
    private final UserRepository<User> userRepository;

    public UserServiceImpl() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public User create(User user) {
        return userRepository.create(user).orElseThrow(()->new UserException("Can not to create User"));
    }

    @Override
    public User update(User user) {
        User userUpd = userRepository.getById(user.getId()).orElseThrow(()-> new UserException("Fail to find user by id: "+user.getId()));
        return userRepository.update(updateFields(user,userUpd)).orElseThrow(()->new UserException("Can not to update User"));
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id).orElseThrow(()-> new UserException("Fail to find user by id: "+id));
    }

    @Override
    public boolean deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public User findByLogin(String s) {
        return userRepository.findByLogin(s).orElseThrow(()->new UserException("Fail to find user by login: "+s));
    }

    @SneakyThrows
    private User updateFields(User user, User userUpd){
        for (Field f:userUpd.getClass().getDeclaredFields()){
            f.setAccessible(true);
            String fieldName = f.getName();

            if (!"id".equals(fieldName) && f.get(user)!=null){
                f.set(userUpd,f.get(user));
            }
        }
        return userUpd;
    }
}
