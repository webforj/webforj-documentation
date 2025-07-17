package pages.BusyIndicatorPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;
import config.RouteConfig;

public class BusyDemoPage extends BasePage {

    private static final String ROUTE = RouteConfig.BUSY_DEMO;

    private final Locator busyIndicator;
    private final Locator nameInput;
    private final Locator passwordInput;
    private final Locator submitButton;

    public BusyDemoPage(Page page) {
        super(page);

        busyIndicator = page.locator("dwc-loading:has-text('Submitting form... Please wait.')");
        nameInput = page.locator("#field-1");
        passwordInput = page.locator("#field-2");
        submitButton = page.locator("dwc-button:has-text('Submit')");
    }

    public static String getRoute() {
        return ROUTE;
    }

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