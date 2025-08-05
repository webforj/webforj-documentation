package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableSortingPage extends BasePage {
    private static final String ROUTE = "tablesorting";

    private final Locator titleSorting;
    private final Locator firstTitleCell;

    public TableSortingPage(Page page) {
        super(page);

        titleSorting = page.locator("dwc-table >> text=Title");
        firstTitleCell = page.locator("tr[part*='row'] td[part*='cell'] div[part='cell-label']")
                .first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTitleSorting() {
        return titleSorting;
    }

    public Locator getFirstTitleCell() {
        return firstTitleCell;
    }
}