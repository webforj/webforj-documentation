package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger("PlaywrightFramework");

    public static void info(String message, Object... args) {
        logger.info(message, args);
    }

    public static void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public static void error(String message, Object... args) {
        logger.error(message, args);
    }

    public static void error(String message, Throwable throwable) {
        logger.error("{}: {}", message, throwable.getMessage(), throwable);
    }

    public static void errorConcise(String message, Throwable throwable) {
        logger.error("{}: {} ({})", message, throwable.getMessage(), throwable.getClass().getSimpleName());
    }

    public static void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    public static boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public static void debugIfEnabled(String message, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(message, args);
        }
    }

    public static void testResult(String testName, boolean passed, String details) {
        if (passed) {
            logger.info("✓ {}", testName);
        } else {
            logger.error("✗ {} - {}", testName, details);
        }
    }
}