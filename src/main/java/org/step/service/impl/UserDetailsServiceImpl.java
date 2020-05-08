package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.step.model.User;
import org.step.model.UserDetailsImpl;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.security.Role;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository<User> userRepository;
    private final AuthoritiesRepository<User> authoritiesRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository<User> userUserRepository,
                                  AuthoritiesRepository<User> authoritiesRepository) {
        this.userRepository = userUserRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = authoritiesRepository.findAuthoritiesByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Role not found"));

        user.setRole(Role.valueOf(role));

        return UserDetailsImpl.create(user);
    }
}
