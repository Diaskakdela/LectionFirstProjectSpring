package org.step.data;

import org.step.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {

    public static List<User> employees = new ArrayList<>(Arrays.asList(
            new User(1L, "first", "first"),
            new User(2L, "second", "second"),
            new User(3L, "third", "third"),
            new User(4L, "fourth", "fourth"),
            new User(5L, "fifth", "fifth"))
    );
}
