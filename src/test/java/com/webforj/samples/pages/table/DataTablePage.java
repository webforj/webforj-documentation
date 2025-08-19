package com.webforj.samples.pages.table;

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
    private final Locator paginatorLastPage;
    private final Locator paginatorNextPage;
    private final Locator paginatorFirstPage;
    private final Locator paginatorPreviousPage;
    private final Locator currentPageNavigator;
    private final Locator paginationText;
    private final Locator firstCheckbox;
    private final Locator lastPageNumber;
    private final Locator tableHost;
    private final Locator athleteCells;
    private final Locator choiceBoxHost;
    private final Locator paginatorHost;

    public DataTablePage(Page page) {
        super(page);

        this.tableHost = page.locator("dwc-table");
        this.choiceBoxHost = page.locator("dwc-choicebox");
        this.paginatorHost = page.locator("dwc-navigator").filter(new Locator.FilterOptions().setHas(
                page.locator("[part~='layout-numbered'], [part~='layout-preview']")));

        this.searchInput = page.locator("dwc-field").locator("input");
        this.tableRows = tableHost.locator("tbody tr[data-row]");
        athleteCells = tableHost.locator("tbody td[data-cell$='-athlete']");
        this.entriesDropdown = choiceBoxHost.locator("dwc-dropdown");
        this.entriesTen = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("10").setExact(true));
        this.entriesTwentyfive = page.getByRole(AriaRole.OPTION,
                new Page.GetByRoleOptions().setName("25").setExact(true));
        this.entriesFifty = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("50").setExact(true));
        this.entriesHundred = page.getByRole(AriaRole.OPTION,
                new Page.GetByRoleOptions().setName("100").setExact(true));
        this.paginatorLastPage = paginatorHost.locator("button[part~='button-last']");
        this.paginatorNextPage = paginatorHost.locator("button[part~='button-next']");
        this.paginatorFirstPage = paginatorHost.locator("button[part~='button-first']");
        this.paginatorPreviousPage = paginatorHost.locator("button[part~='button-previous']");
        this.currentPageNavigator = paginatorHost.locator("[part~='layout-preview']");
        this.paginationText = paginatorHost.locator("[part~='layout-preview']");
        this.firstCheckbox = tableHost.locator("dwc-checkbox").first().locator("input[type='checkbox']");
        this.lastPageNumber = paginatorHost.locator("div[part='layout layout-numbered']").locator("button")
                .last();
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
        return paginatorLastPage;
    }

    public Locator getPaginatorNextPage() {
        return paginatorNextPage;
    }

    public Locator getPaginatorFirstPage() {
        return paginatorFirstPage;
    }

    public Locator getPaginatorPreviousPage() {
        return paginatorPreviousPage;
    }

    public Locator getCurrentPageNavigator() {
        return currentPageNavigator;
    }

    public Locator getPaginationText() {
        return paginationText;
    }

    public Locator getFirstCheckbox() {
        return firstCheckbox;
    }

    public Locator getLastPageNumber() {
        return lastPageNumber;
    }

    public Locator getAthleteCells() {
        return athleteCells;
    }

    public Locator goToSpecificPage(int pageNumber) {
        return paginatorHost.getByRole(
                AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("Goto page " + pageNumber).setExact(true));
    }

    public static String getRoute() {
        return ROUTE;
    }
}