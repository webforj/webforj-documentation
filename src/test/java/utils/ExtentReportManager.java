package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import config.RunConfig;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enhanced Singleton class to manage ExtentReports instance with better
 * reporting capabilities
 */
public class ExtentReportManager {
    private static final ExtentReports extentReports;
    private static final int MAX_REPORTS_TO_KEEP = 10;

    private static final ConcurrentHashMap<String, ExtentTest> activeTests = new ConcurrentHashMap<>();

    static {
        File reportsDir = new File(RunConfig.getReportsDir());
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        cleanupOldReports();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss"));
        String reportPath = RunConfig.getReportsDir() + "/test-report-" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        configureSparkReporter(sparkReporter);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        setSystemInformation();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LoggerUtil.info("Flushing ExtentReports on JVM shutdown...");
            extentReports.flush();
        }));

        LoggerUtil.info("Enhanced Extent Reports initialized at: " + reportPath);
    }

    private static void configureSparkReporter(ExtentSparkReporter sparkReporter) {
        sparkReporter.config().setDocumentTitle("Playwright Test Execution Report");
        sparkReporter.config().setReportName("WebForJ Component Testing Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        sparkReporter.config().setCss("""
                .test-detail-body { font-size: 14px; }
                .step-details { background-color: #f8f9fa; padding: 10px; margin: 5px 0; border-radius: 5px; }
                .browser-info { background-color: #e3f2fd; padding: 8px; border-radius: 4px; margin-bottom: 10px; }
                .error-details { background-color: #ffebee; padding: 10px; border-radius: 5px; }
                .test-step { margin: 5px 0; padding: 5px; }
                .step-pass { color: #4caf50; }
                .step-fail { color: #f44336; }
                .step-info { color: #2196f3; }
                """);
    }

    private static void setSystemInformation() {
        extentReports.setSystemInfo("Operating System",
                System.getProperty("os.name") + " " + System.getProperty("os.version"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Java Vendor", System.getProperty("java.vendor"));
        extentReports.setSystemInfo("Playwright Version", "1.50.0");
        extentReports.setSystemInfo("Test Framework", "JUnit 5");
        extentReports.setSystemInfo("Execution Time",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        extentReports.setSystemInfo("User", System.getProperty("user.name"));
        extentReports.setSystemInfo("Working Directory", System.getProperty("user.dir"));

        extentReports.setSystemInfo("Configured Browsers", String.join(", ", RunConfig.getBrowsers()));
        extentReports.setSystemInfo("Headless Mode", String.valueOf(RunConfig.isHeadless()));
        extentReports.setSystemInfo("Slow Motion", RunConfig.getSlowMo() + "ms");
        extentReports.setSystemInfo("Retry Count", RunConfig.getRetryCount() + " times");

    }

    public static ExtentReports getInstance() {
        return extentReports;
    }

    public static ExtentTest createTest(String testName, String description, String browserType, String testClass) {
        String enhancedDescription = String.format(
                "<div class='browser-info' style='color: black;'>" +
                        "<strong>Browser:</strong> %s<br>" +
                        "<strong>Test Class:</strong> %s<br>" +
                        "<strong>Description:</strong> %s" +
                        "</div>",
                browserType.toUpperCase(), testClass, description);

        ExtentTest test = extentReports.createTest(testName + " [" + browserType + "]", enhancedDescription);
        test.assignCategory(browserType.toUpperCase());
        test.assignCategory(testClass);

        String testKey = Thread.currentThread().getId() + "_" + testName;
        activeTests.put(testKey, test);

        return test;
    }

    public static ExtentTest getCurrentTest(String testName) {
        String testKey = Thread.currentThread().getId() + "_" + testName;
        return activeTests.get(testKey);
    }

    public static void removeTest(String testName) {
        String testKey = Thread.currentThread().getId() + "_" + testName;
        activeTests.remove(testKey);
    }

    public static void logStep(ExtentTest test, Status status, String stepDescription) {
        if (test != null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String logMessage = String.format("[%s] %s", timestamp, stepDescription);
            test.log(status, logMessage);
        }
    }

    public static void logInfo(ExtentTest test, String message) {
        logStep(test, Status.INFO, message);
    }

    public static void logPass(ExtentTest test, String message) {
        logStep(test, Status.PASS, message);
    }

    public static void logFail(ExtentTest test, String message) {
        logStep(test, Status.FAIL, message);
    }

    public static void logWarning(ExtentTest test, String message) {
        logStep(test, Status.WARNING, message);
    }

    public static void logBrowserAction(ExtentTest test, String action, String element, String value) {
        if (test != null) {
            String message = String.format(
                    "<div class='test-step'>" +
                            "<strong>Action:</strong> %s<br>" +
                            "<strong>Element:</strong> %s<br>" +
                            "%s" +
                            "</div>",
                    action,
                    element,
                    value != null ? "<strong>Value:</strong> " + value : "");
            test.log(Status.INFO, message);
        }
    }

    public static void logAssertion(ExtentTest test, String assertion, String expected, String actual, boolean passed) {
        if (test != null) {
            Status status = passed ? Status.PASS : Status.FAIL;
            String statusClass = passed ? "step-pass" : "step-fail";

            String message = String.format(
                    "<div class='test-step %s'>" +
                            "<strong>Assertion:</strong> %s<br>" +
                            "<strong>Expected:</strong> %s<br>" +
                            "<strong>Actual:</strong> %s<br>" +
                            "<strong>Result:</strong> %s" +
                            "</div>",
                    statusClass,
                    assertion,
                    expected != null ? expected : "N/A",
                    actual != null ? actual : "N/A",
                    passed ? "✓ PASSED" : "✗ FAILED");
            test.log(status, message);
        }
    }

    public static void logNavigation(ExtentTest test, String url) {
        logBrowserAction(test, "Navigate", "URL", url);
    }

    public static void logElementAction(ExtentTest test, String action, String locator) {
        logBrowserAction(test, action, locator, null);
    }

    public static void logWait(ExtentTest test, String waitType, String element) {
        logBrowserAction(test, "Wait for " + waitType, element, null);
    }

    public static void logFailureWithContext(ExtentTest test, Throwable throwable, String currentUrl, String testStep) {
        if (test != null) {
            String errorMessage = String.format(
                    "<div class='error-details' style='color: black;'>" +
                            "<h4 style='color: black;'>Test Failure Details</h4>" +
                            "<strong style='color: black;'>Current Step:</strong> <span style='color: black;'>%s</span><br>" +
                            "<strong style='color: black;'>Current URL:</strong> <span style='color: black;'>%s</span><br>" +
                            "<strong style='color: black;'>Error Type:</strong> <span style='color: black;'>%s</span><br>" +
                            "<strong style='color: black;'>Error Message:</strong> <span style='color: black;'>%s</span><br>" +
                            "<strong style='color: black;'>Stack Trace:</strong><br style='color: black;'><pre style='color: black;'>%s</pre>" +
                            "</div>",
                    testStep != null ? testStep : "Unknown",
                    currentUrl != null ? currentUrl : "N/A",
                    throwable.getClass().getSimpleName(),
                    throwable.getMessage(),
                    getStackTraceAsString(throwable));

            test.fail(errorMessage);
        }
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
            // Limit stack trace length for readability
            if (sb.length() > 3000) {
                sb.append("... (truncated)");
                break;
            }
        }
        return sb.toString();
    }

    private static void cleanupOldReports() {
        File reportsDir = new File(RunConfig.getReportsDir());
        File[] reportFiles = reportsDir.listFiles((dir, name) -> name.endsWith(".html"));

        if (reportFiles == null || reportFiles.length <= MAX_REPORTS_TO_KEEP) {
            return;
        }
        Arrays.sort(reportFiles, Comparator.comparingLong(File::lastModified).reversed());

        for (int i = MAX_REPORTS_TO_KEEP; i < reportFiles.length; i++) {
            if (reportFiles[i].delete()) {
                LoggerUtil.info("Deleted old report: " + reportFiles[i].getName());
            } else {
                LoggerUtil.warn("Failed to delete old report: " + reportFiles[i].getName());
            }
        }

        LoggerUtil.info("Report cleanup completed. Keeping " + MAX_REPORTS_TO_KEEP + " most recent reports.");
    }

    public static void flushReport() {
        extentReports.flush();
    }
}