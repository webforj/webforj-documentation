package com.webforj.samples.pages.applayout.fullnavbar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AppLayoutFullNavbarPage {


    private static final String ROUTE = "applayoutfullnavbar";

    private final Locator header;

    public AppLayoutFullNavbarPage(Page page) {
        this.header = page.locator("header");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHeaderText() {
        return header;
    }

}
