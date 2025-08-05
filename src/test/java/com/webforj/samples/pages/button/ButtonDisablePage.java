package com.webforj.samples.pages.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class ButtonDisablePage extends BasePage {

    private static final String ROUTE = "buttondisable";

    private final Locator disabledButton;
    private final Locator emailField;

    public ButtonDisablePage(Page page) {
        super(page);

        disabledButton = page.locator("dwc-button.hydrated >> button");
        emailField = page.locator("dwc-field[type='email'] >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDisabledButton() {
        return disabledButton;
    }

    public Locator getEmailField() {
        return emailField;
    }
}