package com.webforj.samples.config;

public class RunConfig {
  private static final String DEFAULT_BROWSER = "chromium";
  private static final boolean DEFAULT_HEADLESS = true;
  private static final int DEFAULT_TIMEOUT = 30000;
  private static final int DEFAULT_SLOW_MO = 0;
  private static final boolean IS_CI = "true".equalsIgnoreCase(System.getenv("CI"));

  static {
    // Parse webforj.e2e if provided
    String e2eProps = System.getProperty("webforj.e2e");
    if (e2eProps != null && !e2eProps.isEmpty()) {
      parseE2EProps(e2eProps);
    }
  }

  private static void parseE2EProps(String propsString) {
    try {
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

  public static String getBrowser() {
    String browser = System.getProperty("browser");
    if (browser == null) {
      browser = System.getProperty("playwright.browser");
    }
    if (browser == null) {
      browser = System.getenv("BROWSER");
    }
    if (browser == null) {
      browser = DEFAULT_BROWSER;
    }
    return browser.toLowerCase();
  }

  public static boolean isCI() {
    return IS_CI;
  }

  public static boolean isHeadless() {
    if (IS_CI) {
      return true;
    }
    return getConfigBoolean("headless", DEFAULT_HEADLESS);
  }

  public static int getDefaultTimeout() {
    return getConfigInt("default.timeout", DEFAULT_TIMEOUT);
  }

  public static int getSlowMo() {
    return getConfigInt("slowmo", DEFAULT_SLOW_MO);
  }

}
