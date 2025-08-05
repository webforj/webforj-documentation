package com.webforj.samples.pages.table;

import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableDynamicStylingPage extends BasePage {
    private static final String ROUTE = "tabledynamicstyling";

    public TableDynamicStylingPage(Page page) {
        super(page);
    }

    public static String getRoute() {
        return ROUTE;
    }
}