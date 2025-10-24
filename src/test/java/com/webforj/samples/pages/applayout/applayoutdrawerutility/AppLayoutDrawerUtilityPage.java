package com.webforj.samples.pages.applayout.applayoutdrawerutility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AppLayoutDrawerUtilityPage {

    private static final String ROUTE = "applayoutdrawerutility";

    private final Locator headerText;

    public AppLayoutDrawerUtilityPage(Page page) {

        this.headerText = page.getByText("Joe Smith");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHeaderText() {
        return headerText;
    }

}
