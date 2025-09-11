package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TableColumnComparatorPage {
    private static final String ROUTE = "tablecolumncomparator";

    private final Locator numberColumnHeader;
    private final Locator numberCells;

    public TableColumnComparatorPage(Page page) {
        
        this.numberColumnHeader = page.getByRole(AriaRole.TABLE)
                .filter(new Locator.FilterOptions().setHasText("Number"));
        this.numberCells = page.getByRole(AriaRole.TABLE).locator("td[data-column='Number']")
                .locator("div[part='cell-label']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNumberColumnHeader() {
        return numberColumnHeader;
    }

    public Locator getNumberCells() {
        return numberCells;
    }
}