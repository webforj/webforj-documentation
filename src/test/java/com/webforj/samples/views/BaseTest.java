package com.webforj.samples.views;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.webforj.samples.config.RunConfig;

import java.nio.file.*;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    private Playwright playwright;
    private Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    public void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(RunConfig.isHeadless())
                        .setSlowMo(RunConfig.getSlowMo()));
    }

    @BeforeEach
    public void setupTest(TestInfo testInfo) {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setIgnoreHTTPSErrors(true));

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
    }

    @AfterEach
    public void teardownTest(TestInfo testInfo) {
        if (context != null) {
            String testName = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9]", "");

            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("./target/playwright-traces/" + testName + ".zip")));

            context.close();
        }
    }

    @AfterAll
    public void teardownBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    protected void navigateToRoute(String route) {
        page.navigate("http://localhost:8998/" + route);
        // Wait for the page to be fully loaded
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }
}
