package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableColumnComparatorPage extends BasePage {
    private static final String ROUTE = "tablecolumncomparator";

    private final Locator numberColumnHeader;
    private final Locator numberCells;
    private final Locator dataTableHost;

    public TableColumnComparatorPage(Page page) {
        super(page);

        this.dataTableHost = page.locator("dwc-table");

        this.numberColumnHeader = dataTableHost.locator("th[data-column='Number']");
        this.numberCells = dataTableHost.locator("td[data-cell*='-Number'] div[part='cell-label']");

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