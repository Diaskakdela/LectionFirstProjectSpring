package org.step.repository;

import org.step.model.User;

import java.util.Optional;

public interface AuthoritiesRepository<T extends User> {

    boolean saveAuthorities(T t);

    Optional<String> findAuthoritiesByUserId(Long id);
}
