package testng;

import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class TestBase {

    protected String testName;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        testName = method.getName();
    }
}
