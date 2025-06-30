// Create this as a separate file
package utils.annotations;

import java.lang.reflect.Field;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import tests.BaseTest;

public class BrowserTestExecutionHandler implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        // Get the BaseTest instance - handle nested test classes
        Object testInstance = context.getRequiredTestInstance();
        BaseTest baseTest = null;
        
        if (testInstance instanceof BaseTest) {
            baseTest = (BaseTest) testInstance;
        } else {
            // Handle nested test classes by finding the outer class instance
            // that extends BaseTest
            try {
                Field outerField = testInstance.getClass().getDeclaredField("this$0");
                outerField.setAccessible(true);
                Object outerInstance = outerField.get(testInstance);
                if (outerInstance instanceof BaseTest) {
                    baseTest = (BaseTest) outerInstance;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Log error but continue test execution
                System.err.println("Failed to get outer class instance: " + e.getMessage());
            }
        }
        
        // Call onTestFailure if BaseTest instance was found
        if (baseTest != null) {
            baseTest.onTestFailure(throwable);
        }
        
        // Re-throw the exception so JUnit knows the test failed
        throw throwable;
    }
}