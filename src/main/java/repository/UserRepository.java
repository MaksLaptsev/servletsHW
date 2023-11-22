package repository;

import java.util.Optional;

public interface UserRepository <T>{
    Optional<T> create(T t);

    Optional<T> update(T t);

    Optional<T> getById(Long id);

    boolean deleteById(Long id);

    Optional<T> findByLogin(String s);
}
