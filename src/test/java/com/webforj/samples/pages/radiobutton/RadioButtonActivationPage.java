package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class RadioButtonActivationPage extends BasePage {

    private static final String ROUTE = "radiobuttonactivation";

    private final Locator activatedRB;

    public RadioButtonActivationPage(Page page) {
        super(page);

        activatedRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Auto Activated")).nth(0);
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getActivatedRB() {
        return activatedRB;
    }
}