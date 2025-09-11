package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class RadioButtonTextPage {

    private static final String ROUTE = "radiobuttontext";

    private final Locator leftAlignedRB;

    public RadioButtonTextPage(Page page) {

        this.leftAlignedRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Left aligned"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getLeftAlignedInput() {
        return leftAlignedRB;
    }
}