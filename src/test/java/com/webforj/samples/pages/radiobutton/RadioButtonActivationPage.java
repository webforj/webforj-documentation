package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonActivationPage extends BasePage {

    private static final String ROUTE = "radiobuttonactivation";

    private final Locator autoActivatedInput;
    private final Locator shadowRootRadio;

    public RadioButtonActivationPage(Page page) {
        super(page);

        this.shadowRootRadio = page.locator("dwc-radio").nth(0);
        this.autoActivatedInput = shadowRootRadio.locator("input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getAutoActivatedInput() {
        return autoActivatedInput;
    }
}