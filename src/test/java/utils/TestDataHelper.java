package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Helper class for loading and managing test data
 */
public class TestDataHelper {
    private static final Map<String, Properties> testDataCache = new HashMap<>();

    /**
     * Load test data from a properties file
     * 
     * @param fileName Name of the file (without path)
     * @return Properties object containing the test data
     */
    public static Properties loadTestData(String fileName) {
        // Check cache first
        if (testDataCache.containsKey(fileName)) {
            return testDataCache.get(fileName);
        }

        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/testdata/" + fileName);
            props.load(fis);
            fis.close();

            // Cache the data
            testDataCache.put(fileName, props);

            LoggerUtil.info("Loaded test data from: " + fileName);
            return props;
        } catch (IOException e) {
            LoggerUtil.error("Failed to load test data from: " + fileName, e);
            return props;
        }
    }

    /**
     * Get a value from test data
     * 
     * @param fileName     Name of the file (without path)
     * @param key          Property key
     * @param defaultValue Default value if property is not found
     * @return Property value
     */
    public static String getTestData(String fileName, String key, String defaultValue) {
        Properties props = loadTestData(fileName);
        return props.getProperty(key, defaultValue);
    }

    /**
     * Get a value from test data
     * 
     * @param fileName Name of the file (without path)
     * @param key      Property key
     * @return Property value or null if not found
     */
    public static String getTestData(String fileName, String key) {
        return getTestData(fileName, key, null);
    }

    /**
     * Clear the test data cache
     */
    public static void clearCache() {
        testDataCache.clear();
        LoggerUtil.info("Test data cache cleared");
    }
}