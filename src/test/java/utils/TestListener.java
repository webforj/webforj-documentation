package utils;

import java.util.Optional;

import org.junit.jupiter.api.extension.*;

/**
 * JUnit 5 test listener for handling test lifecycle events
 * This can be registered in your test classes for additional reporting
 */
public class TestListener implements BeforeTestExecutionCallback, AfterTestExecutionCallback,
        TestWatcher {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        LoggerUtil.info("Starting test: " + context.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        LoggerUtil.info("Completed test: " + context.getDisplayName());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        LoggerUtil.info("Test passed: " + context.getDisplayName());

    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LoggerUtil.error("Test failed: " + context.getDisplayName() + " - " + cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        LoggerUtil.warn("Test aborted: " + context.getDisplayName() + " - " + cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        LoggerUtil.info("Test disabled: " + context.getDisplayName() +
                reason.map(r -> " - " + r).orElse(""));
    }
}