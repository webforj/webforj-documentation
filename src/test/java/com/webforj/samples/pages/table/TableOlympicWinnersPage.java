package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableOlympicWinnersPage extends BasePage {
    private static final String ROUTE = "tableolympicwinners";

    private final Locator totalHeader;
    private final Locator athleteHeader;
    private final Locator totalRow;
    private final Locator athleteRow;
    private final Locator rows;
    private final Locator firstRow;
    private final Locator lastRow;
    private final Locator tableHost;

    public TableOlympicWinnersPage(Page page) {
        super(page);

        this.tableHost = page.locator("dwc-table");

        this.totalHeader = tableHost.locator("th:nth-child(7)").first();
        this.athleteHeader = tableHost.locator("th:nth-child(1)").first();
        this.totalRow = tableHost.locator("td:nth-child(7)").first();
        this.athleteRow = tableHost.locator("td:nth-child(1)").first();

       this.rows = tableHost.locator("tr[data-row]");
       this.firstRow = rows.first();
       this.lastRow = rows.last();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTotalHeader() {
        return totalHeader;
    }

    public Locator getAthleteHeader() {
        return athleteHeader;
    }

    public Locator getTotalRow() {
        return totalRow;
    }

    public Locator getAthleteRow() {
        return athleteRow;
    }

    public Locator getRows() {
        return rows;
    }

    public Locator getFirstRow() {
        return firstRow;
    }

    public Locator getLastRow() {
        return lastRow;
    }
}