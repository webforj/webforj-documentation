package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableFilteringPage extends BasePage {

    private static final String ROUTE = "tablefiltering";

    private final Locator titleFilterInput;
    private final Locator tableRows;
    private final Locator firstTitleCell;
    private final Locator tableHost;
    private final Locator fieldHost;

    public TableFilteringPage(Page page) {
        super(page);

        this.tableHost = page.locator("dwc-table");
        this.fieldHost = page.locator("dwc-field");

        this.titleFilterInput = fieldHost.locator("#field-1");
        this.tableRows = tableHost.locator("tbody tr[part*='row']");
        this.firstTitleCell = tableHost.locator("tr td").first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTitleFilterInput() {
        return titleFilterInput;
    }

    public Locator getTableRows() {
        return tableRows;
    }

    public Locator getFirstTitleCell() {
        return firstTitleCell;
    }

    public void filterByTitle(String title) {
        titleFilterInput.fill(title);
    }

    public int tableRowCount() {
        return tableRows.count();
    }
}
