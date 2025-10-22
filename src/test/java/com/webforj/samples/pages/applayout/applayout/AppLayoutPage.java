package com.webforj.samples.pages.applayout.applayout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AppLayoutPage {

    private static final String ROUTE = "applayout";

    private final Locator dashboardLink;

    public AppLayoutPage(Page page) {
        this.dashboardLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dashboard"));

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDashboardLink() {
        return dashboardLink;
    }
}
