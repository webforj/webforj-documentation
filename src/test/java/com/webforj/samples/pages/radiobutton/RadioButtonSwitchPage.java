package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonSwitchPage extends BasePage {

    private static final String ROUTE = "radiobuttonswitch";

    private final Locator switchRadio;
    private final Locator switchInput;

    public RadioButtonSwitchPage(Page page) {
        super(page);

        switchRadio = page.locator("dwc-radio:has-text('Switch')");
        switchInput = switchRadio.locator("input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSwitchRadio() {
        return switchRadio;
    }

    public Locator getSwitchInput() {
        return switchInput;
    }
}