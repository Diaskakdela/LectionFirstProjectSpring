package org.step.repository;

import org.step.model.User;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface UserRepository<T extends User> extends AuthoritiesRepository<User> {

    Optional<T> findByUsername(String username);

    T save(T user);

    boolean delete(T user);

    Optional<T> findById(Long id);

    List<User> findAll();

    T update(T user);

    Optional<T> login(T t);
}
