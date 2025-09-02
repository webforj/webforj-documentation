package com.webforj.samples.pages.table;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import com.webforj.samples.pages.BasePage;

public class DataTablePage extends BasePage {
    private static final String ROUTE = "datatable";

    private final Locator searchInput;
    private final Locator tableRows;
    private final Locator entriesDropdown;
    private final Locator entriesTen;
    private final Locator entriesTwentyfive;
    private final Locator entriesFifty;
    private final Locator entriesHundred;
    private final Locator firstButton;
    private final Locator prevButton;
    private final Locator nextButton;
    private final Locator lastButton;

    public DataTablePage(Page page) {
        super(page);

        this.searchInput = page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search"));
        this.tableRows = page.getByRole(AriaRole.TABLE).filter().locator("[data-row]");

        this.entriesDropdown = page.locator("dwc-dropdown");

        this.entriesTen = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("10").setExact(true));
        this.entriesTwentyfive = page.getByRole(AriaRole.OPTION,
                new Page.GetByRoleOptions().setName("25").setExact(true));
        this.entriesFifty = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("50").setExact(true));
        this.entriesHundred = page.getByRole(AriaRole.OPTION,
                new Page.GetByRoleOptions().setName("100").setExact(true));

        this.firstButton = page.getByLabel("Goto first page");
        this.prevButton = page.getByLabel("Goto previous page");
        this.nextButton = page.getByLabel("Goto next page");
        this.lastButton = page.getByLabel("Goto last page");
    }

    public void searchAthlete(String athleteName) {
        searchInput.click();
        page.keyboard().type(athleteName);
    }

    public int getRowCount() {
        return tableRows.count();
    }

    public Locator getSearchInput() {
        return searchInput;
    }

    public Locator getTableRows() {
        return tableRows;
    }

    public Locator getEntriesDropdown() {
        return entriesDropdown;
    }

    public Locator getEntriesTen() {
        return entriesTen;
    }

    public Locator getEntriesTwentyfive() {
        return entriesTwentyfive;
    }

    public Locator getEntriesFifty() {
        return entriesFifty;
    }

    public Locator getEntriesHundred() {
        return entriesHundred;
    }

    public Locator getPaginatorLastPage() {
        return lastButton;
    }

    public Locator getPaginatorNextPage() {
        return nextButton;
    }

    public Locator getPaginatorFirstPage() {
        return firstButton;
    }

    public Locator getPaginatorPreviousPage() {
        return prevButton;
    }

    public Locator getPaginationText(String text) {
        return page.getByText(text);
    }

    public Locator getAthleteCells(String athlete) {
        return page.getByText(athlete);
    }

    public Locator goToSpecificPage(int n) {
        String regex = String.format("^\\s*Goto page %d\\s*$", n);
        return page.getByLabel(Pattern.compile(regex));
    }

    public static String getRoute() {
        return ROUTE;
    }
}