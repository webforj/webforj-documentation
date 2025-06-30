package pages;

import com.microsoft.playwright.Page;
import utils.LoggerUtil;

/**
 * Base Page Object class that all page objects will extend
 */
public abstract class BasePage {
    protected Page page;
    protected String pageUrl;
    protected String pageTitle;

    public BasePage(Page page) {
        this.page = page;
    }

    /**
     * Navigate to the page URL
     */
    public void navigate() {
        LoggerUtil.info("Navigating to " + pageUrl);
        page.navigate(pageUrl);
    }

    /**
     * Check if the current page title matches the expected title
     * 
     * @return true if the title matches, false otherwise
     */
    public boolean isPageTitleCorrect() {
        String actualTitle = page.title();
        LoggerUtil.info("Checking page title. Expected: " + pageTitle + ", Actual: " + actualTitle);
        return actualTitle.contains(pageTitle);
    }

    /**
     * Wait for the page to load completely
     */
    public void waitForPageLoad() {
        page.waitForLoadState();
    }
}