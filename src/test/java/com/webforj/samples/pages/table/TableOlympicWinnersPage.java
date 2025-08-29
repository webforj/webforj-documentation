package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class TableOlympicWinnersPage extends BasePage {
    private static final String ROUTE = "tableolympicwinners";

    private final Locator totalHeader;
    private final Locator athleteHeader;
    private final Locator totalRow;
    private final Locator athleteRow;
    private final Locator firstRow;
    private final Locator lastRow;

    public TableOlympicWinnersPage(Page page) {
        super(page);

        this.totalHeader = page.getByRole(AriaRole.TABLE).locator("th[data-column='total']");
        this.athleteHeader = page.getByRole(AriaRole.TABLE).locator("th[data-column='athlete']");
        this.totalRow = page.getByRole(AriaRole.CELL)
                .filter(new Locator.FilterOptions().setHasText("total")).nth(0);
        this.athleteRow = page.getByRole(AriaRole.CELL)
                .filter(new Locator.FilterOptions().setHasText("athlete")).nth(0);

        this.firstRow = page.getByRole(AriaRole.TABLE).filter().locator("[data-row]").first();
        this.lastRow = page.getByRole(AriaRole.TABLE).filter().locator("[data-row]").last();
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

    public Locator getFirstRow() {
        return firstRow;
    }

    public Locator getLastRow() {
        return lastRow;
    }
}