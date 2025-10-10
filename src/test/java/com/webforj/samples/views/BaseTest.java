package com.webforj.samples.views;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.webforj.samples.config.RunConfig;

import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    Playwright playwright;
    Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    public void setupBrowser() {
        playwright = Playwright.create();
        String name = RunConfig.getBrowser().toLowerCase();
        BrowserType type;
        switch (name) {
            case "firefox":
                type = playwright.firefox();
                break;
            case "webkit":
                type = playwright.webkit();
                break;
            default:
                type = playwright.chromium();
                break;
        }
        browser = type.launch(new BrowserType.LaunchOptions()
                .setHeadless(RunConfig.isHeadless())
                .setSlowMo(RunConfig.getSlowMo()));

        PlaywrightAssertions.setDefaultAssertionTimeout(15000);
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

        page.addStyleTag(new Page.AddStyleTagOptions()
                .setContent("* { transition: none !important; animation: none !important; }"));
    }
}
