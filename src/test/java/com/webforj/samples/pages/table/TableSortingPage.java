package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class TableSortingPage extends BasePage {
    private static final String ROUTE = "tablesorting";

    private final Locator titleSorting;
    private final Locator firstTitleCell;

    public TableSortingPage(Page page) {
        super(page);

        this.titleSorting = page.getByRole(AriaRole.CELL).nth(0);
        this.firstTitleCell = page.getByRole(AriaRole.ROW).nth(2).getByRole(AriaRole.CELL).nth(0);
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