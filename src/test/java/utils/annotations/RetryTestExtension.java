package utils.annotations;

import config.RunConfig;
import tests.BaseTest;

import org.junit.jupiter.api.extension.*;
import utils.LoggerUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * JUnit 5 extension for retrying tests on failure
 */
public class RetryTestExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod()
                .map(method -> method.isAnnotationPresent(RetryTest.class))
                .orElse(false);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        RetryTest retryTest = context.getRequiredTestMethod().getAnnotation(RetryTest.class);
        int retries = retryTest.value() > 0 ? retryTest.value() : RunConfig.getRetryCount();

        LoggerUtil.info("Setting up retry test with " + retries + " retries");

        return IntStream.rangeClosed(1, retries + 1)
                .mapToObj(invocation -> new RetryTestInvocationContext(invocation, retries));
    }

    private static class RetryTestInvocationContext implements TestTemplateInvocationContext {
        private final int invocation;
        private final int retries;

        public RetryTestInvocationContext(int invocation, int retries) {
            this.invocation = invocation;
            this.retries = retries;
        }

        @Override
        public String getDisplayName(int invocation) {
            return String.format("Attempt %d of %d", this.invocation, retries + 1);
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return List.of(new RetryTestExecutionExtension(invocation, retries));
        }
    }

    private static class RetryTestExecutionExtension implements TestExecutionExceptionHandler {
        private final int invocation;
        private final int retries;

        public RetryTestExecutionExtension(int invocation, int retries) {
            this.invocation = invocation;
            this.retries = retries;
        }

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
                    LoggerUtil.error("Failed to get outer class instance: " + e.getMessage());
                }
            }

            if (baseTest != null) {
                baseTest.onTestFailure(throwable);
            }

            if (invocation <= retries) {
                LoggerUtil.warn("Test failed on attempt " + invocation + " of " + (retries + 1) + ": "
                        + throwable.getMessage());
                LoggerUtil.warn("Retrying...");
            } else {
                LoggerUtil.error("Test failed on final attempt: " + throwable.getMessage());
                throw throwable;
            }
        }
    }
}