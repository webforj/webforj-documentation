package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DataTablePage {
    private static final String ROUTE = "datatable";

    private final Page page;
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

        this.page = page;
        
        this.searchInput = page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search"));
        this.tableRows = page.getByRole(AriaRole.TABLE).filter().locator("[data-row]");

        this.entriesDropdown = page.locator("dwc-dropdown");

        this.entriesTen = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("10").setExact(true));
        this.entriesTwentyfive = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("25").setExact(true));
        this.entriesFifty = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("50").setExact(true));
        this.entriesHundred = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("100").setExact(true));

        this.firstButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Goto first page"));
        this.prevButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Goto previous page"));
        this.nextButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Goto next page"));
        this.lastButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Goto last page"));
    }

    public void searchAthlete(String athleteName) {
        searchInput.fill(athleteName);
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
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Goto page " + n));
    }

    public static String getRoute() {
        return ROUTE;
    }
}