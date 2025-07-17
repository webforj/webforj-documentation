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

    private static final ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();

    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected static ExtentReports extentReports;
    protected static final ConcurrentHashMap<String, ExtentTest> testReports = new ConcurrentHashMap<>();
    protected ExtentTest testReport;

    protected String browserType;
    protected String currentTestName;

    static {
        extentReports = ExtentReportManager.getInstance();
    }

    protected Playwright getPlaywright() {
        Playwright playwright = threadPlaywright.get();
        if (playwright == null) {
            LoggerUtil.info("Creating new Playwright instance for thread: " + Thread.currentThread().getId());
            playwright = Playwright.create();
            threadPlaywright.set(playwright);
        }
        return playwright;
    }

    @BeforeEach
    public void setupTest(TestInfo testInfo) {
    try {
        String displayName = testInfo.getDisplayName();
        currentTestName = displayName;

        List<String> browsers = RunConfig.getBrowsers();
        browserType = browsers.isEmpty() ? "chromium" : browsers.get(0);

        for (String browser : browsers) {
            if (displayName.contains(browser)) {
                browserType = browser;
                break;
            }
        }

        String testClassName = testInfo.getTestClass().map(Class::getSimpleName).orElse("Unknown");
        testReport = ExtentReportManager.createTest(
                displayName,
                "Test for WebForJ components",
                browserType,
                testClassName);

        String testId = displayName + "_" + Thread.currentThread().getId();
        testReports.put(testId, testReport);

        browser = launchBrowser(browserType);
        context = browser.newContext();
        page = context.newPage();

    } catch (Exception e) {
        if (testReport != null) {
            ExtentReportManager.logFailureWithContext(testReport, e, "N/A", "Test Setup");
        }
        throw e;
    }
}

    @AfterEach
    public void teardownTest(TestInfo testInfo) {
        try {
            if (testReport != null) {
                ExtentReportManager.logInfo(testReport, "🧹  Cleanup...");
            }

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
                ExtentReportManager.logPass(testReport, "✅ Cleanup completed");
            }

            testReports.remove(testInfo.getDisplayName() + "_" + Thread.currentThread().getId());

        } catch (Exception e) {
            LoggerUtil.warn("Exception during teardown: " + e.getMessage());
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
                .setSlowMo(RunConfig.getSlowMo())
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