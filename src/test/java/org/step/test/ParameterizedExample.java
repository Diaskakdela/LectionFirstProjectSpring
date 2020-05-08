package org.step.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParameterizedExample {

    @Parameterized.Parameter(0)
    public int first;
    @Parameterized.Parameter(1)
    public int second;
    @Parameterized.Parameter(2)
    public int multiply;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{1, 2, 2}, {2, 2, 4}, {3, 4, 12}};
        return Arrays.asList(data);
    }

    @Test
    public void test() {
        MyClass myClass = new MyClass(first, second);
        Assert.assertEquals(myClass.getMultiply(), multiply);
    }

    private static class MyClass {

        private final int first;
        private final int second;

        public MyClass(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getMultiply() {
            return first * second;
        }
    }
}
