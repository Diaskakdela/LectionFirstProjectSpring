package org.step.repository;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.step.model.User;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.security.Role;

import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryImplIT {

    private static UserRepository<User> userRepository;
    private static AuthoritiesRepository<User> authoritiesRepository;
    private static List<User> userList;
    private static User user;
    private static Long idAfterSaving;

    @BeforeClass
    public static void setup() {
        userRepository = new UserRepositoryImpl();
        authoritiesRepository = new UserRepositoryImpl();
        user = new User("first", "first");
        User save = userRepository.save(user);
        user.setId(save.getId());
        userList = userRepository.findAll();
    }

    @Test
    public void shouldReturnAllUsersFromDatabase() {
        List<User> all = userRepository.findAll();

        all.forEach(user -> {
            System.out.println(user.getId());
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
        });

        Assert.assertTrue(all.contains(user));
        Assert.assertEquals(userList.size(), all.size());
    }

    @Test
    public void shouldSaveUserToDatabase() {
        User user = new User("second", "second");

        User afterSaving = userRepository.save(user);

        idAfterSaving = afterSaving.getId();

        List<User> all = userRepository.findAll();

        Assert.assertNotNull(afterSaving);
        Assert.assertEquals(userList.size() + 1, all.size());
        Assert.assertTrue(all.contains(user));
    }

    @Test
    public void shouldSaveAuthoritiesToDatabase() {
        Long id = user.getId();
        User user = new User(id, "second", "second");

        user.setRole(Role.ROLE_USER);

        boolean isAuthoritiesSaved = authoritiesRepository.saveAuthorities(user);

        Assert.assertTrue(isAuthoritiesSaved);
    }

    @AfterClass
    public static void afterClass() {
        User fromSaveTest = new User(idAfterSaving, "second", "second");

        userRepository.delete(fromSaveTest);
        userRepository.delete(user);

        userList.remove(user);

        List<User> all = userRepository.findAll();

        Assert.assertEquals(userList.size(), all.size());
        Assert.assertFalse(all.contains(fromSaveTest));
    }
}
