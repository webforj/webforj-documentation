package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TableOlympicWinnersPage {
    private static final String ROUTE = "tableolympicwinners";

    private final Locator firstRow;
    private final Locator lastRow;

    public TableOlympicWinnersPage(Page page) {

        this.firstRow = page.getByRole(AriaRole.TABLE).filter().locator("[data-row]").first();
        this.lastRow = page.getByRole(AriaRole.TABLE).filter().locator("[data-row]").last();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFirstRow() {
        return firstRow;
    }

    public Locator getLastRow() {
        return lastRow;
    }
}