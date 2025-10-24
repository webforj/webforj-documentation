package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AlertThemesPage {

    private static final String ROUTE = "alertthemes";

    private final Locator successAlert;

    public AlertThemesPage(Page page) {
        this.successAlert = page.locator("dwc-alert").filter(new Locator.FilterOptions().setHasText("This is an alert with the SUCCESS theme!"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSuccessAlert() {
        return successAlert;
    }

}
