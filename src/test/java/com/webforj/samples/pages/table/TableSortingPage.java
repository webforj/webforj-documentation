package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableSortingPage extends BasePage {
    private static final String ROUTE = "tablesorting";

    private final Locator titleSorting;
    private final Locator firstTitleCell;
    private final Locator tableHost;

    public TableSortingPage(Page page) {
        super(page);

        this.tableHost = page.locator("dwc-table");

        this.titleSorting = tableHost.locator("text=Title");
        this.firstTitleCell = tableHost.locator("tr[part*='row-first'] td[part*='cell-first']");
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