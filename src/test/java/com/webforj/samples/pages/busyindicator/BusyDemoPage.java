package com.webforj.samples.pages.busyindicator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BusyDemoPage {

    private static final String ROUTE = "busydemo";

    private final Locator busyIndicator;
    private final Locator nameInput;

    public BusyDemoPage(Page page) {
        this.busyIndicator = page.getByRole(AriaRole.DIALOG);
        this.nameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Name"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getBusyIndicator() {
        return busyIndicator;
    }

    public Locator getNameInput() {
        return nameInput;
    }
}
