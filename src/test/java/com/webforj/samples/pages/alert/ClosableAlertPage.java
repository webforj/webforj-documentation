package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ClosableAlertPage extends BasePage {

    private static final String ROUTE = "closablealert";

    private final Locator closableAlertButton;
    private final Locator showAlertButton;
    private final Locator closableAlert;
    private final Locator alertHost;
    private final Locator buttonHost;
    private final Locator alertButtonHost;
    

    public ClosableAlertPage(Page page) {
        super(page);

        this.alertHost = page.locator("dwc-alert");
        this.closableAlert = alertHost.locator("div[role='alert']");
        this.buttonHost = alertHost.locator("dwc-icon-button[part='close-button']");
        this.closableAlertButton = buttonHost.locator("button.nfv");
        this.alertButtonHost = page.locator("dwc-button:has-text('Show Alert')");
        this.showAlertButton = alertButtonHost.locator("button[type='button']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getClosableAlert() {
        return closableAlert;
    }

    public Locator getClosableAlertButton() {
        return closableAlertButton;
    }

    public Locator getShowAlertButton() {
        return showAlertButton;
    }
}