package org.step.test;

import org.junit.Assert;
import org.junit.Test;
import org.step.model.User;
import org.step.data.Data;

import java.util.List;

public class TestExampleSecond {

    @Test
    public void test() {
        List<User> employees = Data.employees;
        User user = new User(2L, "second", "second");

        int actualSize = 5;

        int size = employees.size();
        User userFromList = employees.get(0);
        boolean contains = employees.contains(user);

        Assert.assertEquals(actualSize, size);
        Assert.assertEquals(userFromList, new User(1L, "first", "first"));
        Assert.assertTrue(contains);
    }
}
