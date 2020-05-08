package org.step.service;

import org.step.model.User;

public interface AuthoritiesService<T extends User> {

    boolean saveAuthorities(T t);

    String getAuthority(Long id);
}
