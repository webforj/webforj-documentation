package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class RadioButtonGroupPage extends BasePage {

    private static final String ROUTE = "radiobuttongroup";

    private final Locator stronglyDisagreeRB;
    private final Locator disagreeRB;

    public RadioButtonGroupPage(Page page) {
        super(page);

        this.stronglyDisagreeRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Strongly Disagree").setExact(true));
        this.disagreeRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Disagree").setExact(true));

        
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getStronglyDisagreeRB() {
        return stronglyDisagreeRB;
    }

    public Locator getDisagreeRB() {
        return disagreeRB;
    }
}