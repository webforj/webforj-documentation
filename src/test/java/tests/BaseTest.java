package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import config.RunConfig;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.*;

import utils.ExtentReportManager;
import utils.LoggerUtil;

/**
 * Optimized Base Test class for faster execution
 */
public class BaseTest {

    // Thread-local Playwright instances - one per thread
    private static final ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();

    // Per-test resources
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
     * Fast setup - creates new browser for each test but reuses Playwright instance
     */
    @BeforeEach
    public void setupTest(TestInfo testInfo) {
        try {
            String displayName = testInfo.getDisplayName();
            currentTestName = displayName;

            // Create lightweight test report
            String testClassName = testInfo.getTestClass().map(Class::getSimpleName).orElse("Unknown");
            testReport = ExtentReportManager.createTest(
                    displayName,
                    "Fast browser test for WebForJ components",
                    browserType != null ? browserType : "chromium",
                    testClassName);

            String testId = displayName + "_" + Thread.currentThread().getId();
            testReports.put(testId, testReport);

            // Get browser type from configuration
            List<String> browsers = RunConfig.getBrowsers();
            browserType = browsers.isEmpty() ? "chromium" : browsers.get(0);

            // Launch browser (no reuse)
            browser = launchBrowser(browserType);
            context = browser.newContext();
            page = context.newPage();

            ExtentReportManager.logInfo(testReport, "✅ Fast test setup completed");

        } catch (Exception e) {
            if (testReport != null) {
                ExtentReportManager.logFailureWithContext(testReport, e, "N/A", "Test Setup");
            }
            throw e;
        }
    }

    /**
     * Fast teardown - closes browser after each test
     */
    @AfterEach
    public void teardownTest(TestInfo testInfo) {
        try {
            if (testReport != null) {
                ExtentReportManager.logInfo(testReport, "🧹 Fast cleanup...");
            }

            // Close page, context and browser
            if (page != null) {
                page.close();
            }

            if (context != null) {
                context.close();
            }

            if (browser != null) {
                browser.close();
            }

            if (testReport != null) {
                ExtentReportManager.logPass(testReport, "✅ Fast cleanup completed");
            }

            // Clean up test report
            testReports.remove(testInfo.getDisplayName() + "_" + Thread.currentThread().getId());

        } catch (Exception e) {
            LoggerUtil.warn("Exception during fast teardown: " + e.getMessage());
        }
    }

    /**
     * Close shared resources after all tests in the thread
     */
    @AfterAll
    public static void teardownSharedResources() {
        Playwright playwright = threadPlaywright.get();
        if (playwright != null) {
            playwright.close();
            threadPlaywright.remove();
        }
    }

    /**
     * Launch browser with optimized settings
     */
    private Browser launchBrowser(String browserType) {
        LoggerUtil.info("Launching " + browserType + " browser");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(RunConfig.isHeadless())
                .setSlowMo(50)
                .setArgs(List.of(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-extensions",
                    "--disable-gpu",
                    "--disable-background-timer-throttling"
                ));

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
     * Navigate to route using configured port
     */
    protected void navigateToRoute(String route) {
        int port = RunConfig.getPort();
        String url = String.format("http://localhost:%d/%s", port, route);
        ExtentReportManager.logNavigation(testReport, url);
        page.navigate(url);
        ExtentReportManager.logPass(testReport, "✅ Navigated to: " + route);
    }

    /**
     * Enhanced test failure handling
     */
    public void onTestFailure(Throwable throwable) {
        LoggerUtil.error("Test failed: " + throwable.getMessage());

        if (page != null && testReport != null) {
            try {
                String currentUrl = page.url();
                ExtentReportManager.logFailureWithContext(testReport, throwable, currentUrl, "Test Execution");
            } catch (Exception e) {
                ExtentReportManager.logWarning(testReport, "⚠️ Error during failure handling: " + e.getMessage());
            }
        }
    }
}