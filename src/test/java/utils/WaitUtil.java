package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import config.RunConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Supplier;

public class WaitUtil {

    private static final int DEFAULT_TIMEOUT = RunConfig.getDefaultTimeout();

    public static boolean waitForState(Locator locator, WaitForSelectorState state) {
        return waitForState(locator, state, DEFAULT_TIMEOUT);
    }

    public static boolean waitForState(Locator locator, WaitForSelectorState state, int timeout) {
        try {
            LoggerUtil.info("Waiting for element to be " + state);
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(state)
                    .setTimeout(timeout));
            return true;
        } catch (TimeoutError e) {
            LoggerUtil.warn("Timeout while waiting for state: " + state);
            return false;
        }
    }

    public static boolean waitForVisible(Locator locator) {
        return waitForState(locator, WaitForSelectorState.VISIBLE);
    }

    public static boolean waitForVisible(Locator locator, int timeout) {
        return waitForState(locator, WaitForSelectorState.VISIBLE, timeout);
    }

    public static boolean waitForVisible(Locator locator, Locator locator2) {
        return waitForState(locator, WaitForSelectorState.VISIBLE);
    }

    public static boolean waitForVisible(Locator locator, Locator locator2, Locator locator3) {
        return waitForState(locator, WaitForSelectorState.VISIBLE);
    }

    public static boolean waitForHidden(Locator locator) {
        return waitForState(locator, WaitForSelectorState.HIDDEN);
    }

    public static boolean waitForAttached(Locator locator) {
        return waitForState(locator, WaitForSelectorState.ATTACHED);
    }

    public static boolean waitForAttached(Locator locator, int timeout) {
        return waitForState(locator, WaitForSelectorState.ATTACHED, timeout);
    }

    public static boolean waitForDetached(Locator locator) {
        return waitForState(locator, WaitForSelectorState.DETACHED);
    }

    public static void waitForNavigation(Page page, Runnable action) {
        try {
            page.waitForNavigation(() -> action.run());
        } catch (TimeoutError e) {
            LoggerUtil.warn("Timeout while waiting for navigation");
        }
    }

    public static boolean waitForUrl(Page page, String urlPattern) {
        try {
            page.waitForURL(urlPattern);
            return true;
        } catch (TimeoutError e) {
            return false;
        }
    }

    public static boolean waitForLoadState(Page page, LoadState state) {
        try {
            page.waitForLoadState(state);
            return true;
        } catch (TimeoutError e) {
            return false;
        }
    }

    public static boolean waitForLoadState(Page page, LoadState state, Runnable action) {
        try {
            action.run();
            return waitForLoadState(page, state);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean waitUntil(Supplier<Boolean> condition, int timeout, int pollInterval) {
        long endTime = System.currentTimeMillis() + timeout;

        while (System.currentTimeMillis() < endTime) {
            if (condition.get()) {
                return true;
            }

            try {
                Thread.sleep(pollInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        return false;
    }

    public static boolean waitUntil(Supplier<Boolean> condition) {
        return waitUntil(condition, DEFAULT_TIMEOUT, 100);
    }

    /*
     * Helper method to assert timeout error when clicking a locator which is not
     * clickable as expected.
     */
    public static void assertClickTimeout(Locator locator, String elementDescription) {
        PlaywrightException exception = assertThrows(PlaywrightException.class, () -> {
            locator.click(new Locator.ClickOptions().setTimeout(1000));
        }, "Expected timeout when clicking: " + elementDescription);

        assertTrue(exception.getMessage().contains("Timeout"),
                "Expected timeout error for: " + elementDescription);
    }
}