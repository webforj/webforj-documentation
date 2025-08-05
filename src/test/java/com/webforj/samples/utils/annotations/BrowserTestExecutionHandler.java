// Create this as a separate file
package com.webforj.samples.utils.annotations;

import java.lang.reflect.Field;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import com.webforj.samples.views.BaseTest;

public class BrowserTestExecutionHandler implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        Object testInstance = context.getRequiredTestInstance();
        BaseTest baseTest = null;

        if (testInstance instanceof BaseTest) {
            baseTest = (BaseTest) testInstance;
            } else {
            try {
                Field outerField = testInstance.getClass().getDeclaredField("this$0");
                outerField.setAccessible(true);
                Object outerInstance = outerField.get(testInstance);
                if (outerInstance instanceof BaseTest) {
                    baseTest = (BaseTest) outerInstance;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("Failed to get outer class instance: " + e.getMessage());
            }
        }

        throw throwable;
    }
}
