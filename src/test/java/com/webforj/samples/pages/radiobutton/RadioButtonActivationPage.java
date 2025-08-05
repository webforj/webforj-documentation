package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonActivationPage extends BasePage {

    private static final String ROUTE = "radiobuttonactivation";

    private final Locator autoActivatedInput;

    public RadioButtonActivationPage(Page page) {
        super(page);

        autoActivatedInput = page.locator("dwc-radio:has-text('Auto Activated')").nth(0).locator("input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getAutoActivatedInput() {
        return autoActivatedInput;
    }
}