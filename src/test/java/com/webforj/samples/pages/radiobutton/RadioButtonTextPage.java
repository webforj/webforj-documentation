package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.SupportedLanguage;

public class RadioButtonTextPage {

    private static final String ROUTE = "radiobuttontext";

    private final Locator leftAlignedRB;

    public RadioButtonTextPage(Page page) {

        this.leftAlignedRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Left aligned"));
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getLeftAlignedInput() {
        return leftAlignedRB;
    }
}
