package org.step.service;

import org.step.model.User;

import java.util.List;

public interface UserService<T extends User> extends AuthoritiesService<User> {

    boolean save(T user, boolean isAdmin);

    boolean delete(T user);

    T findById(Long id);

    List<User> findAll();

    T update(T user);

    User login(User user);
}
