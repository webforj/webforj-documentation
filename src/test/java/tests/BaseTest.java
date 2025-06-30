package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import config.RunConfig;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.*;

import utils.ExtentReportManager;
import utils.LoggerUtil;
import utils.ScreenshotUtil;
import utils.WaitUtil;

/**
 * Enhanced Base Test class with comprehensive reporting
 */
public class BaseTest {

    // Thread-local Playwright instances - one per thread
    private static final ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();

    // Instance resources
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    // Reporting
    protected static ExtentReports extentReports;
    protected static final ConcurrentHashMap<String, ExtentTest> testReports = new ConcurrentHashMap<>();
    protected ExtentTest testReport;

    // Current browser type and test tracking
    protected String browserType;
    protected String currentTestName;
    protected String currentTestStep = "Test Initialization";

    // Static initializer for shared resources
    static {
        extentReports = ExtentReportManager.getInstance();
    }

    /**
     * Get or create Playwright instance for current thread
     */
    protected Playwright getPlaywright() {
        Playwright playwright = threadPlaywright.get();
        if (playwright == null) {
            LoggerUtil.info("Creating new Playwright instance for thread: " + Thread.currentThread().getId());
            playwright = Playwright.create();
            threadPlaywright.set(playwright);
        }
        return playwright;
    }

    /**
     * Enhanced setup before each test method
     */
    @BeforeEach
    public void setupBrowser(TestInfo testInfo) {
        try {
            currentTestStep = "Browser Setup";

            // Extract browser type from display name or use default
            List<String> browsers = RunConfig.getBrowsers();
            browserType = browsers.isEmpty() ? "chromium" : browsers.get(0);

            String displayName = testInfo.getDisplayName();
            currentTestName = displayName;

            for (String browser : browsers) {
                if (displayName.contains(browser)) {
                    browserType = browser;
                    break;
                }
            }

            String testId = displayName + "_" + Thread.currentThread().getId();
            LoggerUtil.info("Setting up " + browserType + " browser for test: " + testId);

            // Create enhanced test report
            String testClassName = testInfo.getTestClass().map(Class::getSimpleName).orElse("Unknown");
            testReport = ExtentReportManager.createTest(
                    displayName,
                    "Automated browser test for WebForJ components",
                    browserType,
                    testClassName);

            testReports.put(testId, testReport);

            // Log test start
            ExtentReportManager.logInfo(testReport, "🚀 Starting test execution");
            ExtentReportManager.logInfo(testReport, "Browser: " + browserType.toUpperCase());
            ExtentReportManager.logInfo(testReport, "Test Class: " + testClassName);

            currentTestStep = "Browser Launch";
            ExtentReportManager.logInfo(testReport, "📱 Launching " + browserType + " browser...");

            // Launch browser using thread-local Playwright instance
            browser = launchBrowser(browserType);
            ExtentReportManager.logPass(testReport, "✅ Browser launched successfully");

            currentTestStep = "Browser Context Creation";
            ExtentReportManager.logInfo(testReport, "🔧 Creating browser context...");

            // Create browser context
            context = browser.newContext();
            ExtentReportManager.logPass(testReport, "✅ Browser context created");

            currentTestStep = "Page Creation";
            ExtentReportManager.logInfo(testReport, "📄 Creating new page...");

            // Create page
            page = context.newPage();
            ExtentReportManager.logPass(testReport, "✅ Page created successfully");

            currentTestStep = "Test Ready";
            ExtentReportManager.logInfo(testReport, "✅ Browser setup completed - Ready for test execution");

        } catch (Exception e) {
            if (testReport != null) {
                ExtentReportManager.logFailureWithContext(testReport, e, "N/A", currentTestStep);
            }
            throw e;
        }
    }

    /**
     * Enhanced teardown after each test method
     */
    @AfterEach
    public void teardownBrowser(TestInfo testInfo) {
        try {
            currentTestStep = "Test Cleanup";
            LoggerUtil.info("Tearing down browser after test: " + testInfo.getDisplayName());

            if (testReport != null) {
                ExtentReportManager.logInfo(testReport, "🧹 Starting browser cleanup...");
            }

            if (page != null) {
                if (testReport != null) {
                    ExtentReportManager.logInfo(testReport, "Closing page...");
                }
                page.close();
            }

            if (context != null) {
                if (testReport != null) {
                    ExtentReportManager.logInfo(testReport, "Closing browser context...");
                }
                context.close();
            }

            if (browser != null) {
                if (testReport != null) {
                    ExtentReportManager.logInfo(testReport, "Closing browser...");
                }
                browser.close();
            }

            if (testReport != null) {
                ExtentReportManager.logPass(testReport, "✅ Browser cleanup completed successfully");
            }

            // Remove the test report from the map
            testReports.remove(testInfo.getDisplayName() + "_" + Thread.currentThread().getId());
            ExtentReportManager.removeTest(testInfo.getDisplayName());

        } catch (Exception e) {
            if (testReport != null) {
                ExtentReportManager.logWarning(testReport, "⚠️ Warning during cleanup: " + e.getMessage());
            }
            LoggerUtil.warn("Exception during teardown: " + e.getMessage());
        }
    }

    @AfterAll
    public static void teardownAll() {
        // Close any remaining Playwright instances
        threadPlaywright.remove();
    }

    /**
     * Launch browser based on type with enhanced logging
     */
    private Browser launchBrowser(String browserType) {
        LoggerUtil.info("Launching " + browserType + " browser");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(RunConfig.isHeadless())
                .setSlowMo(RunConfig.getSlowMo());

        if (testReport != null) {
            ExtentReportManager.logInfo(testReport,
                    String.format("Browser Configuration - Headless: %s, SlowMo: %dms",
                            RunConfig.isHeadless(), RunConfig.getSlowMo()));
        }

        switch (browserType.toLowerCase()) {
            case "firefox":
                return getPlaywright().firefox().launch(launchOptions);
            case "webkit":
                return getPlaywright().webkit().launch(launchOptions);
            case "chromium":
            default:
                return getPlaywright().chromium().launch(launchOptions);
        }
    }

    /**
     * Enhanced test failure handling
     */
    public void onTestFailure(Throwable throwable) {
        LoggerUtil.error("Test failed: " + throwable.getMessage());

        if (page != null && testReport != null) {
            try {
                String currentUrl = page.url();

                // Log failure with comprehensive context
                ExtentReportManager.logFailureWithContext(testReport, throwable, currentUrl, currentTestStep);

                // Take screenshot
                String screenshotPath = ScreenshotUtil.takeScreenshot(
                        page,
                        browserType + "_" + currentTestName + "_" + System.currentTimeMillis());

                File screenshotFile = new File(screenshotPath);

                if (screenshotFile.exists()) {
                    String absolutePath = screenshotFile.getAbsolutePath();
                    testReport.addScreenCaptureFromPath(absolutePath, "💥 Failure Screenshot - " + currentTestStep);
                    ExtentReportManager.logInfo(testReport, "📸 Screenshot captured: " + absolutePath);
                } else {
                    ExtentReportManager.logWarning(testReport, "⚠️ Failed to capture screenshot");
                    LoggerUtil.error("Screenshot file does not exist: " + screenshotPath);
                }
            } catch (Exception e) {
                ExtentReportManager.logWarning(testReport, "⚠️ Error during failure handling: " + e.getMessage());
                LoggerUtil.error("Failed to handle test failure", e);
            }
        }
    }

    /**
     * Navigate to URL with logging
     */
    protected void navigateToUrl(String url) {
        currentTestStep = "Navigation to " + url;
        ExtentReportManager.logNavigation(testReport, url);
        page.navigate(url);
        ExtentReportManager.logPass(testReport, "✅ Successfully navigated to: " + url);
    }

    /**
     * Navigate to route using configured port
     */
    protected void navigateToRoute(String route) {
        int port = RunConfig.getPort();
        String url = String.format("http://localhost:%d/%s", port, route);
        navigateToUrl(url);
    }
    /**
     * Wait for element to be visible with logging
     */
    protected void waitForVisible(Locator locator) {
        currentTestStep = "Wait for element visibility: " + locator;
        ExtentReportManager.logWait(testReport, "visibility", locator.toString());
        WaitUtil.waitForVisible(locator);
        ExtentReportManager.logPass(testReport, "✅ Element is visible: " + locator);
    }

    /**
     * Click element with logging
     */
    protected void clickElement(Locator locator) {
        currentTestStep = "Click element: " + locator;
        ExtentReportManager.logElementAction(testReport, "Click", locator.toString());
        locator.click();
        ExtentReportManager.logPass(testReport, "✅ Successfully clicked: " + locator);
    }

    /**
     * Assert element is visible with logging
     */
    protected void assertElementVisible(Locator locator) {
        currentTestStep = "Assert element visibility: " + locator;
        boolean isVisible = locator.isVisible();
        ExtentReportManager.logAssertion(testReport, "Element is visible", "true", String.valueOf(isVisible),
                isVisible);

        if (!isVisible) {
            throw new AssertionError("Element is not visible: " + locator);
        }
    }

    /**
     * Assert element has text with logging
     */
    protected void assertElementHasText(Locator locator, String expectedText) {
        currentTestStep = "Assert element text: " + locator;
        String actualText = locator.textContent();
        boolean matches = expectedText.equals(actualText);

        ExtentReportManager.logAssertion(testReport, "Element text matches", expectedText, actualText, matches);

        if (!matches) {
            throw new AssertionError(String.format("Text mismatch for %s. Expected: '%s', Actual: '%s'",
                    locator, expectedText, actualText));
        }
    }

    /**
     * Assert element has attribute with logging
     */
    protected void assertElementHasAttribute(Locator locator, String attributeName, String expectedValue) {
        currentTestStep = "Assert element attribute: " + locator + " [" + attributeName + "]";
        String actualValue = locator.getAttribute(attributeName);
        boolean matches = expectedValue.equals(actualValue);

        ExtentReportManager.logAssertion(testReport,
                "Element attribute '" + attributeName + "' matches",
                expectedValue, actualValue, matches);

        if (!matches) {
            throw new AssertionError(String.format("Attribute mismatch for %s[%s]. Expected: '%s', Actual: '%s'",
                    locator, attributeName, expectedValue, actualValue));
        }
    }

    /**
     * Log custom test step
     */
    protected void logTestStep(String stepDescription) {
        currentTestStep = stepDescription;
        ExtentReportManager.logInfo(testReport, "📋 " + stepDescription);
    }

    /**
     * Log test section
     */
    protected void logTestSection(String sectionName) {
        currentTestStep = sectionName;
        ExtentReportManager.logInfo(testReport, "📁 === " + sectionName + " ===");
    }
}