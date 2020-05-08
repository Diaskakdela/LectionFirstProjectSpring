package org.step.service;

import org.assertj.core.api.Assertions;
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
import org.step.security.Role;
import org.step.service.impl.UserServiceImpl;

import java.util.Optional;
import java.util.Random;

public class UserServiceImplTestSecond {

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
        private Random random;
        random = Mockito.mock(Random.class);
        userRepository = Mockito.init(UserRepositoryImpl.class);
        userService = new UserServiceImpl(userRepository);
         */
//        userService = new UserServiceImpl(userRepository, authoritiesRepository, random);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveUserToDatabase() {
        // Инициализируем тестовые данные
        User user = new User(2L, "second", "second");

        final int passwordInteger = 5;

        // Задаем поведение Mock объектов
        Mockito.when(random.nextInt(Mockito.anyInt()))
                .thenReturn(passwordInteger);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(authoritiesRepository.saveAuthorities(user))
                .thenReturn(true);

        // Вызываем реальный метод
        boolean isSaved = userService.save(user, true);

        // Проверяем сколько раз был вызыван метод у Mock объекта
        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);
        Mockito.verify(authoritiesRepository, Mockito.times(1))
                .saveAuthorities(user);
        Mockito.verify(random, Mockito.times(1))
                .nextInt(Mockito.anyInt());

        // Проверяем полученные данные в методе (тестируем логику)
        Assert.assertTrue(isSaved);
        Assert.assertEquals(Role.ROLE_USER, user.getRole());
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getPassword())
                .startsWith("sec")
                .endsWith("shifr5");
    }

    @Test
    public void shouldReturnFalseBecauseIdIsNullOrZero() {
        // Инициализация тестовых данных
        User user = new User(null, "third", "third");

        final int passwordInteger = 5;

        // Задаем поведение Mock объектов
        Mockito.when(random.nextInt(Mockito.anyInt()))
                .thenReturn(passwordInteger);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        // Вызываем реальный метод
        boolean isSaved = userService.save(user, true);

        // Проверяем сколько раз был вызыван метод у Mock объекта
        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);
        Mockito.verifyZeroInteractions(authoritiesRepository);
        Mockito.verify(random, Mockito.times(1))
                .nextInt(Mockito.anyInt());

        // Проверяем полученные данные в методе (тестируем логику)
        Assert.assertFalse(isSaved);
        Assert.assertNull(user.getRole());
        Assert.assertNull(user.getId());
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getPassword())
                .startsWith("thi")
                .endsWith("shifr5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        // Инициализируем тестовые данные
        final int passwordInteger = 5;

        // Задаем поведение Mock объектов
        Mockito.when(random.nextInt(Mockito.anyInt()))
                .thenReturn(passwordInteger);

        // Вызываем реальный метод
        boolean isSaved = userService.save(null, true);

        // Проверяем сколько раз был вызыван метод у Mock объекта
        Mockito.verifyZeroInteractions(userRepository);
        Mockito.verify(random, Mockito.times(1))
                .nextInt(Mockito.anyInt());

        // Проверяем полученные данные в методе (тестируем логику)
        Assertions.assertThat(isSaved).isFalse();
        Assertions.assertThatThrownBy(() -> {
            throw new IllegalArgumentException("User is null");
        }).hasMessage("User is null");
    }
}
