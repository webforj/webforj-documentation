package com.webforj.samples.views;

import com.microsoft.playwright.*;
import com.webforj.samples.config.RunConfig;

import java.util.List;

import org.junit.jupiter.api.*;

/**
 * Optimized Base Test class for faster execution
 */
public class BaseTest {

    private static final ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();

    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected String browserType;
    protected String currentTestName;

    protected Playwright getPlaywright() {
        Playwright playwright = threadPlaywright.get();
        if (playwright == null) {
            playwright = Playwright.create();
            threadPlaywright.set(playwright);
        }
        return playwright;
    }

    @BeforeEach
    public void setupTest(TestInfo testInfo) {
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

        browser = launchBrowser(browserType);
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void teardownTest(TestInfo testInfo) {
        if (page != null) {
            page.close();
        }

        if (context != null) {
            context.close();
        }

        if (browser != null) {
            browser.close();
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
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(RunConfig.isHeadless())
                .setSlowMo(RunConfig.getSlowMo());

        // Only add args for browsers that support them
        if (!browserType.equalsIgnoreCase("webkit")) {
            launchOptions.setArgs(List.of(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-extensions",
                "--disable-gpu",
                "--disable-background-timer-throttling"
            ));
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
     * Navigate to route using configured port
     */
    protected void navigateToRoute(String route) {
        int port = RunConfig.getPort();
        String url = String.format("http://localhost:%d/%s", port, route);
        page.navigate(url);
    }

}
