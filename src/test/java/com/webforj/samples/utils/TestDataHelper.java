package com.webforj.samples.utils;

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

    public static Properties loadTestData(String fileName) {
        if (testDataCache.containsKey(fileName)) {
            return testDataCache.get(fileName);
        }

        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/testdata/" + fileName);
            props.load(fis);
            fis.close();

            testDataCache.put(fileName, props);

            return props;
        } catch (IOException e) {
            return props;
        }
    }


    public static String getTestData(String fileName, String key, String defaultValue) {
        Properties props = loadTestData(fileName);
        return props.getProperty(key, defaultValue);
    }


    public static String getTestData(String fileName, String key) {
        return getTestData(fileName, key, null);
    }


    public static void clearCache() {
        testDataCache.clear();
    }
}