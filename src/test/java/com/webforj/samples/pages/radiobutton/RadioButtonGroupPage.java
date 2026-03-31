package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.SupportedLanguage;

public class RadioButtonGroupPage {

    private static final String ROUTE = "radiobuttongroup";

    private final Locator stronglyDisagreeRB;
    private final Locator disagreeRB;

    public RadioButtonGroupPage(Page page) {

        this.stronglyDisagreeRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Strongly Disagree").setExact(true));
        this.disagreeRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Disagree").setExact(true));


    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getStronglyDisagreeRB() {
        return stronglyDisagreeRB;
    }

    public Locator getDisagreeRB() {
        return disagreeRB;
    }
}
