package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging
 */
public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger("PlaywrightFramework");

    /**
     * Log info message
     * 
     * @param message Message to log
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Log warning message
     * 
     * @param message Message to log
     */
    public static void warn(String message) {
        logger.warn(message);
    }

    /**
     * Log error message
     * 
     * @param message Message to log
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Log error message with exception
     * 
     * @param message   Message to log
     * @param throwable Exception to log
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Log debug message
     * 
     * @param message Message to log
     */
    public static void debug(String message) {
        logger.debug(message);
    }
}