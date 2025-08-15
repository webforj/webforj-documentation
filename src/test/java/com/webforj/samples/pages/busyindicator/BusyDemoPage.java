package com.webforj.samples.pages.busyindicator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class BusyDemoPage extends BasePage {

    private static final String ROUTE = "busydemo";

    private final Locator busyIndicator;
    private final Locator nameInput;
    private final Locator passwordInput;
    private final Locator submitButton;
    private final Locator busyIndicatorHost;
    private final Locator nameHost;
    private final Locator passwordHost;

    public BusyDemoPage(Page page) {
        super(page);

        this.busyIndicatorHost = page.locator("dwc-loading:has-text('Submitting form... Please wait.')");
        this.busyIndicator = busyIndicatorHost.locator("dwc-backdrop[part='backdrop']");
        this.nameHost = page.locator("dwc-field[type='text']").nth(0);
        this.nameInput = nameHost.locator("input[type='text']");
        this.passwordHost = page.locator("dwc-field[type='text']").nth(1);
        this.passwordInput = passwordHost.locator("input[type='text']");
        this.submitButton = page.locator("dwc-button:has-text('Submit')");
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