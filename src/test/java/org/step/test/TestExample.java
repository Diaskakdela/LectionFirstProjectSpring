package org.step.test;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.service.UserService;
import org.step.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestExample {

    private UserService<User> userService;
    private UserRepository<User> userRepository;
    private AuthoritiesRepository<User> authoritiesRepository;
    private Random random;

    @Before
    public void setup() {
        random = Mockito.mock(Random.class);
        userRepository = Mockito.mock(UserRepositoryImpl.class);

//        userService = new UserServiceImpl(userRepository, authoritiesRepository, random);
    }

    @Test
    public void test() {
        User user = new User(1L, "first", "first");
        userRepository = new UserRepositoryImpl();

        List<User> all = userRepository.findAll();

        Assertions.assertThat(all)
                .contains(user)
                .hasSize(3);
    }

    @Test
    public void test2() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(new User()));

        List<User> all = userService.findAll();

        Assert.assertEquals(all.size(), 1);
    }
}
