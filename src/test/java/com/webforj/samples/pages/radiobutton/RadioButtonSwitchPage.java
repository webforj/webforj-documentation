package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonSwitchPage extends BasePage {

    private static final String ROUTE = "radiobuttonswitch";

    private final Locator switchRadio;

    public RadioButtonSwitchPage(Page page) {
        super(page);

        this.switchRadio = page.locator("dwc-radio.Switch").locator("input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSwitchRadio() {
        return switchRadio;
    }
}