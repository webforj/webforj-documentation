package utils;

import com.microsoft.playwright.Page;
import config.RunConfig;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots
 */
public class ScreenshotUtil {

    /**
     * Take a screenshot and save it to the configured directory
     * 
     * @param page        The Playwright page to take a screenshot of
     * @param browserType The type of browser
     * @return The path to the saved screenshot
     */
    public static String takeScreenshot(Page page, String browserType) {
        // Create directory if it doesn't exist
        File screenshotsDir = new File(RunConfig.getScreenshotsDir());
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        // Generate timestamp for unique filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        // Create filename
        String filename = "screenshot_" + browserType + "_" + timestamp + ".png";

        // Take screenshot using absolute path
        File screenshotFile = new File(screenshotsDir, filename);
        page.screenshot(new Page.ScreenshotOptions().setPath(screenshotFile.toPath()).setFullPage(true));

        LoggerUtil.info("Screenshot saved to: " + screenshotFile.getAbsolutePath());
        return screenshotFile.getAbsolutePath(); // Return absolute path
    }

    /**
     * Take a screenshot with test name and save it to the configured directory
     * 
     * @param page        The Playwright page to take a screenshot of
     * @param testName    The name of the test
     * @param browserType The type of browser
     * @return The path to the saved screenshot
     */
    public static String takeScreenshot(Page page, String testName, String browserType) {
        // Create directory if it doesn't exist
        File screenshotsDir = new File(RunConfig.getScreenshotsDir());
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        // Generate timestamp for unique filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        // Sanitize test name for filename
        String sanitizedTestName = testName.replaceAll("[^a-zA-Z0-9.-]", "_");

        // Create filename
        String filename = sanitizedTestName + "_" + browserType + "_" + timestamp + ".png";

        // Take screenshot
        Path screenshotPath = Paths.get(RunConfig.getScreenshotsDir(), filename);
        page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true));

        LoggerUtil.info("Screenshot saved to: " + screenshotPath);
        return screenshotPath.toString();
    }
}