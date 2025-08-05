package com.webforj.samples.config;

import java.util.Arrays;
import java.util.List;

public class RunConfig {
  private static final String DEFAULT_BROWSERS = "chromium,firefox,webkit";
  private static final boolean DEFAULT_HEADLESS = true; // Default to headless in CI
  private static final int DEFAULT_TIMEOUT = 30000;
  private static final int DEFAULT_RETRY_COUNT = 3;
  private static final String DEFAULT_REPORTS_DIR = "test-results/reports";
  private static final int DEFAULT_SLOW_MO = 0; // No slow motion by default
  private static final int DEFAULT_PORT = 8998; // Match the CI port

  static {
    // Parse webforj.e2e if provided
    String e2eProps = System.getProperty("webforj.e2e");
    if (e2eProps != null && !e2eProps.isEmpty()) {
      parseE2EProps(e2eProps);
    }
  }

  private static void parseE2EProps(String propsString) {
    try {
      // Parse manually to handle browsers=a:b:c case
      String[] pairs = propsString.split(",");

      for (String pair : pairs) {
        pair = pair.trim();
        int eq = pair.indexOf('=');
        if (eq > 0) {
          String key = pair.substring(0, eq).trim();
          String value = pair.substring(eq + 1).trim();

          // For browsers, replace : with , for comma-separated list
          if ("browsers".equals(key)) {
            value = value.replace(':', ',');
          }

          System.setProperty(key, value);
        }
      }
    } catch (Exception e) {
      System.getLogger(RunConfig.class.getName())
          .log(System.Logger.Level.WARNING, "Failed to parse webforj.e2e: " + e.getMessage());
    }
  }

  /**
   * Get a configuration value checking in order:
   * 1. System property (-Dkey=value)
   * 2. Environment variable (KEY=value, with dots replaced by underscores and
   * uppercase)
   * 3. Default value
   */
  private static String getConfig(String key, String defaultValue) {
    // First check system property
    String value = System.getProperty(key);
    if (value != null) {
      return value;
    }

    // Then check environment variable (convert dots to underscores and uppercase)
    String envKey = key.toUpperCase().replace(".", "_");
    value = System.getenv(envKey);
    if (value != null) {
      return value;
    }

    // Finally return default
    return defaultValue;
  }

  private static boolean getConfigBoolean(String key, boolean defaultValue) {
    String value = getConfig(key, null);
    if (value == null) {
      return defaultValue;
    }
    return Boolean.parseBoolean(value);
  }

  private static int getConfigInt(String key, int defaultValue) {
    String value = getConfig(key, null);
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
    String browsersStr = getConfig("browsers", DEFAULT_BROWSERS);
    return Arrays.stream(browsersStr.split(","))
        .map(String::trim)
        .toList();
  }

  public static boolean isHeadless() {
    return getConfigBoolean("headless", DEFAULT_HEADLESS);
  }

  public static int getDefaultTimeout() {
    return getConfigInt("default.timeout", DEFAULT_TIMEOUT);
  }

  public static int getSlowMo() {
    return getConfigInt("slow.mo", DEFAULT_SLOW_MO);
  }

  public static int getRetryCount() {
    return getConfigInt("retry.count", DEFAULT_RETRY_COUNT);
  }

  public static String getReportsDir() {
    return getConfig("reports.dir", DEFAULT_REPORTS_DIR);
  }

  public static int getPort() {
    return getConfigInt("port", DEFAULT_PORT);
  }

}
