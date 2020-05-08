package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.security.Role;
import org.step.service.AuthoritiesService;
import org.step.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService<User> {

    private final UserRepository<User> userRepository;
    private final AuthoritiesRepository<User> authoritiesRepository;
    private final Random random;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository<User> userRepository,
                           AuthoritiesRepository<User> authoritiesRepository,
                           Random random,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.random = random;
        this.authoritiesRepository = authoritiesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(User user) {

        User userFromDB = getUser(user);

        if (isPasswordEqual(userFromDB)) {
            return userFromDB;
        } else {
            throw new IllegalArgumentException("Password is not correct");
        }
    }

    private User getUser(User user) {
        User returnUser = getUserFromDBByLogin(getLogin(user));
        return returnUser;
    }
    private User getUserFromDBByLogin(Optional<User> login) {
        User userFromDB = login.orElseThrow(() -> new IllegalArgumentException("User not found"));

        return userFromDB;
    }
    private Optional<User> getLogin(User user) {
        Optional<User> login = userRepository.login(user);

        return login;
    }
    private boolean isPasswordEqual(User user) {
        if(user.getPassword().equals(user.getPassword())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean save(User user, boolean isAdmin) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        User userAfterSaving = setAndReturnUserWithEncodedPassword(user);

        if (isUserExist(userAfterSaving)) {
            setRoleForNewUser(userAfterSaving, isAdmin);
            return authoritiesRepository.saveAuthorities(userAfterSaving);
        }
        return false;
    }

    private User setAndReturnUserWithEncodedPassword(User user)
    {
        setEncodedPassword(user, getEncodedPassword(user));

        return user;
    }
    private void setEncodedPassword(User user, String encodedPassword)
    {
        user.setPassword(encodedPassword);
    }
    private String getEncodedPassword(User user)
    {
        String passwordAfterEncoding = passwordEncoder.encode(user.getPassword());

        return passwordAfterEncoding;
    }
    private void setRoleForNewUser(User user, boolean isAdmin)
    {
        if (isAdmin) {
            user.setRole(Role.ROLE_ADMIN);
        } else {
            user.setRole(Role.ROLE_USER);
        }
    }
    private boolean isUserExist(User user)
    {
        if(user.getId() !=null && user.getId()!=0){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        return false;
    }


    @Override
    public User findById(Long id) {
        if (isIdNull(id)) {
            return getUserFoundedById(id);
        } else{
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    private boolean isIdNull(Long id) {
        if(id != null || id != 0){
            return true;
        } else {
            return false;
        }
    }
    private User getUserFoundedById(Long id)
    {
        Optional<User> userById = getUserById(id);

        if (isUserFull(userById)) {
            return userById.get();
        } else {
            throw new IllegalStateException("User not found");
        }
    }
    private Optional<User> getUserById(Long id)
    {
        Optional<User> userById = userRepository.findById(id);

        return userById;
    }
    private boolean isUserFull(Optional<User> user)
    {
        if(user.isPresent()){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String getAuthority(Long id) {
        userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("User with ID %d not found", id)));

        return authoritiesRepository.findAuthoritiesByUserId(id)
                .orElse("Your role is unknown");
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        if (isThereAnyUsers(user) == true) {
            returnUserWithEncodedPassword(user);

            return userRepository.update(user);
        } else {
            throw new RuntimeException("User ID is null");
        }
    }

    private boolean isThereAnyUsers(User user) {
        if(user.getId()!=null) {
            return true;
        } else {
            return false;
        }
    }
    private User returnUserWithEncodedPassword(User user){

        user.setPassword(encodePassword(user));

        return user;
    }
    private String encodePassword(User user)
    {
        String passwordAfterEncoding = passwordEncoder.encode(user.getPassword());

        return passwordAfterEncoding;
    }


    @Override
    public boolean saveAuthorities(User user) {
        return false;
    }
}
