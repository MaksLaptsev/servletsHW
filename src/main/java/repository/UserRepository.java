package repository;

public interface UserRepository <T>{
    T create(T t);

    T update(T t);

    T getById(Long id);

    boolean deleteById(Long id);

    T findByLogin(String s);
}
