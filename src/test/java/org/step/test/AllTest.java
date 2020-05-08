package org.step.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {TestExample.class,
        TestExampleSecond.class,
        ParameterizedExample.class}
)
public class AllTest {
}
