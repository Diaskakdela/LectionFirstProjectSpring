package org.step.data;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.step.model.User;

import java.util.List;

public class DataTest {

    private static List<User> customList;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    // Используется каждый раз при запуске нового метода
//    @Before
//    public void beforeSetup() {
//        customList = Data.employees;
//    }

    // Используется только 1 раз при запуске класса
    @BeforeClass
    public static void beforeClassSetup() {
        customList = Data.employees;
    }

    @Test
    @Ignore("This is deprecated method")
    public void shouldBeEqualsExpectedSizeAndListSize() {
        final int sizeExpected = 5;

        Assert.assertEquals(sizeExpected, customList.size());
    }

    @Test(timeout = 1000)
    public void shouldContainUser_WithFitArray_And_NullObjectShouldBeNull() {
        User forTest = new User(1L, "first", "first");

        final int sizeExpected = 5;

        Assert.assertTrue(customList.contains(forTest));
        Assert.assertArrayEquals(new int[]{2,3,4}, new int[]{2,3,4});
        Assert.assertNull(null);
        Assert.assertNotNull(forTest);
        Assert.assertEquals(sizeExpected, customList.size());
    }

    //(expected = IllegalStateException.class)
    @Test
    public void shouldSaveUser() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("I'am exception");

        User user = new User(6L, "six", "six");

        if (user.getId() == 6) {
            throw new IllegalStateException("I'am exception");
        } else {
            customList.add(user);
        }
        Assert.assertTrue(customList.contains(user));
        Assert.assertEquals(6, customList.size());
    }

    // После каждого метода
//    @After
//    public void after() {
//        User user = new User(6L, "six", "six");
//        customList.remove(user);
//
//        Assert.assertEquals(5, customList.size());
//    }

    // После прогона всего класса
    @AfterClass
    public static void afterClass() {
        User user = new User(6L, "six", "six");
        customList.remove(user);

        Assert.assertEquals(5, customList.size());
    }
}
