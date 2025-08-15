package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonTextPage extends BasePage {

    private static final String ROUTE = "radiobuttontext";

    private final Locator leftAlignedInput;

    public RadioButtonTextPage(Page page) {
        super(page);

        this.leftAlignedInput = page.locator("dwc-radio").nth(1).locator("input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getLeftAlignedInput() {
        return leftAlignedInput;
    }
}