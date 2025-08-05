package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ClosableAlertPage extends BasePage {

    private static final String ROUTE = "closablealert";

    private final Locator closableAlert;
    private final Locator closableAlertText;
    private final Locator closableAlertButton;
    private final Locator showAlertButton;

    public ClosableAlertPage(Page page) {
        super(page);

        closableAlert = page.locator("dwc-alert[theme='info']");
        closableAlertText = page.locator("text='Heads up! This alert can be dismissed.'");
        closableAlertButton = page.locator("dwc-icon-button.hydrated");
        showAlertButton = page.locator("dwc-button:has-text('Show alert')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getClosableAlert() {
        return closableAlert;
    }

    public Locator getClosableAlertText() {
        return closableAlertText;
    }

    public Locator getClosableAlertButton() {
        return closableAlertButton;
    }

    public Locator getShowAlertButton() {
        return showAlertButton;
    }
}