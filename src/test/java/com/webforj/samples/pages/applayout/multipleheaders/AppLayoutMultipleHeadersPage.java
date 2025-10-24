package com.webforj.samples.pages.applayout.multipleheaders;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AppLayoutMultipleHeadersPage {

    private static final String ROUTE = "applayoutmultipleheaders";

    private final Locator dwcToolbar;

    public AppLayoutMultipleHeadersPage(Page page) {
        this.dwcToolbar = page.locator("dwc-toolbar");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcToolbar() {
        return dwcToolbar;
    }

}
