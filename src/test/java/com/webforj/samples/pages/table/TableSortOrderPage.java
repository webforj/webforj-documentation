package com.webforj.samples.pages.table;

import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableSortOrderPage extends BasePage {
    private static final String ROUTE = "tablesortorder";

    public TableSortOrderPage(Page page) {
        super(page);
    }

    public static String getRoute() {
        return ROUTE;
    }
}