package org.step.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.service.impl.UserServiceImpl;

import java.util.Random;

public class UserServiceImplTestOld {

    @Mock
    private Random random;
    @Mock
    private UserRepository<User> userRepository;
    @Mock
    private AuthoritiesRepository<User> authoritiesRepository;
    @InjectMocks
    private UserService<User> userService;

    @Before
    public void setup() {
        /*
        Mock метод заменяет реально существующий объект на его обертку
        В дальнейшем его использовании мы будем имитировать его работу
        userRepository = Mockito.mock(UserRepositoryImpl.class);
         */
//        userService = new UserServiceImpl(userRepository, authoritiesRepository, random);
        MockitoAnnotations.initMocks(this);
    }

    @Test(timeout = 200)
    public void shouldSetPasswordWithShifr() {
        User user = new User(1L, "eights", "fifth");

        /*
        Имитация работы метода save, при котором мы говорим, что пользватель
        успешно будет сохранен в базу данных, и вернется значение boolean - true
         */
        Mockito.when(userRepository.save(user)).thenReturn(user);

        boolean save = userService.save(user, true);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assert.assertTrue(save);
        Assert.assertTrue(user.getPassword().endsWith("shifr"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        boolean save = userService.save(null, true);

        Mockito.verify(userRepository, Mockito.never())
                .save(Mockito.any(User.class));
        Assert.assertFalse(save);
    }
}
