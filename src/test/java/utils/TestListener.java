package utils;

import java.util.Optional;
import org.junit.jupiter.api.extension.*;


public class TestListener implements BeforeTestExecutionCallback, AfterTestExecutionCallback,
        TestWatcher {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        LoggerUtil.debug("Starting: {}", context.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        LoggerUtil.testResult(context.getDisplayName(), true, null);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LoggerUtil.errorConcise("Test failed: " + context.getDisplayName(), cause);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        LoggerUtil.warn("Test aborted: {} - {}", context.getDisplayName(),
                       cause != null ? cause.getMessage() : "Unknown reason");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        LoggerUtil.debug("Test disabled: {}{}", context.getDisplayName(),
                        reason.map(r -> " - " + r).orElse(""));
    }
}