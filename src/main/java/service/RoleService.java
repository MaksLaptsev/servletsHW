package service;

public interface RoleService <T>{
    T create(T t);

    T update(T t);

    T getById(Long id);

    boolean deleteById(Long id);

    T findByRole(String s);
}
