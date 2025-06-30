package pages.BusyIndicatorPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

/**
 * Page Object for Busy Basics functionality
 */
public class BusyDemoPage extends BasePage {

    private static final String ROUTE = "busydemo";

    // BusyDemo locators
    private final Locator busyIndicator;
    private final Locator nameInput;
    private final Locator passwordInput;
    private final Locator submitButton;

    public BusyDemoPage(Page page) {
        super(page);
        
        // Initialize BusyDemo locators
        busyIndicator = page.locator("dwc-loading[dwc-id='10']");
        nameInput = page.locator("#field-1");
        passwordInput = page.locator("#field-2");
        submitButton = page.locator("dwc-button[dwc-id='15']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // BusyDemo getters
    public Locator getBusyIndicator() {
        return busyIndicator;
    }

    public Locator getNameInput() {
        return nameInput;
    }

    public Locator getPasswordInput() {
        return passwordInput;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }
} 