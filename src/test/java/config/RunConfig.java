package config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RunConfig {
    private static final Properties properties = new Properties();
    private static boolean isInitialized = false;

    private static final String DEFAULT_BROWSERS = "chromium";
    private static final boolean DEFAULT_HEADLESS = true;
    private static final int DEFAULT_TIMEOUT = 30000;
    private static final int DEFAULT_RETRY_COUNT = 3;
    private static final String DEFAULT_REPORTS_DIR = "test-results/reports";
    private static final int DEFAULT_SLOW_MO = 50;
    private static final int DEFAULT_PORT = 8080;

    public static void initialize() {
        if (isInitialized) {
            return;
        }

        try {
            var inputStream = RunConfig.class.getClassLoader().getResourceAsStream("config.properties");
            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            }
            isInitialized = true;
        } catch (IOException e) {
            System.out.println("❌ Error loading config.properties: " + e.getMessage() + ", using defaults");
        }
    }

    public static String getStringProperty(String key, String defaultValue) {
        initialize();
        return properties.getProperty(key, defaultValue);
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        initialize();
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public static int getIntProperty(String key, int defaultValue) {
        initialize();
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static List<String> getBrowsers() {
        String ciEnv = System.getenv("CI");
        String testEnv = System.getenv("TEST_ENV");

        if (!"true".equals(ciEnv) && !"ci".equalsIgnoreCase(testEnv)) {
            String devBrowser = System.getenv("DEV_BROWSER");
            if (devBrowser != null) {
                return Arrays.asList(devBrowser);
            }
            return Arrays.asList("chromium");
        }

        String browsersStr = getStringProperty("browsers", DEFAULT_BROWSERS);
        return Arrays.asList(browsersStr.split(","));
    }

    public static boolean isHeadless() {
        return getBooleanProperty("headless", DEFAULT_HEADLESS);
    }

    public static int getDefaultTimeout() {
        return getIntProperty("default.timeout", DEFAULT_TIMEOUT);
    }

    public static int getSlowMo() {
        return getIntProperty("slow.mo", DEFAULT_SLOW_MO);
    }

    public static int getRetryCount() {
        return getIntProperty("retry.count", DEFAULT_RETRY_COUNT);
    }

    public static String getReportsDir() {
        return getStringProperty("reports.dir", DEFAULT_REPORTS_DIR);
    }

    public static int getPort() {
        return getIntProperty("port", DEFAULT_PORT);
    }

    public static boolean isDevelopmentMode() {
        String ciEnv = System.getenv("CI");
        String testEnv = System.getenv("TEST_ENV");
        return !"true".equals(ciEnv) && !"ci".equalsIgnoreCase(testEnv);
    }
}