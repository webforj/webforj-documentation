package utils;

import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import config.RunConfig;

/**
 * Utility class for managing browser instances
 */
public class BrowserManager {

    /**
     * Launch a browser based on type
     * 
     * @param playwright  Playwright instance
     * @param browserType Type of browser to launch
     * @return Browser instance
     */
    public static Browser launchBrowser(Playwright playwright, String browserType) {
        LoggerUtil.info("Launching " + browserType + " browser");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(RunConfig.isHeadless())
                .setSlowMo(RunConfig.getSlowMo());

        switch (browserType.toLowerCase()) {
            case "firefox":
                return playwright.firefox().launch(launchOptions);
            case "webkit":
                return playwright.webkit().launch(launchOptions);
            case "chromium":
            default:
                return playwright.chromium().launch(launchOptions);
        }
    }

    public static String getNextBrowserType(String currentBrowserType) {
        List<String> browsers = RunConfig.getBrowsers();
        int currentIndex = browsers.indexOf(currentBrowserType);
        int nextIndex = (currentIndex + 1) % browsers.size();
        return browsers.get(nextIndex);
    }
}