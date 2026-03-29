package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.SupportedLanguage;

public class TableFilteringPage {

    private final Page page;
    private static final String ROUTE = "tablefiltering";

    private final Locator titleFilterInput;
    private final Locator tableRows;

    public TableFilteringPage(Page page) {
        this.page = page;
        
        this.titleFilterInput = page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search"));
        this.tableRows = page.getByRole(AriaRole.TABLE).locator("tbody tr[part*='row']");
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getTitleFilterInput() {
        return titleFilterInput;
    }

    public Locator getTableRows() {
        return tableRows;
    }

    public void filterByTitle(String title) {
        titleFilterInput.fill(title);
    }

    public int tableRowCount() {
        return tableRows.count();
    }

    public Locator verifyTitle(String title) {
        return page.getByRole(AriaRole.CELL,
                new Page.GetByRoleOptions().setName(title));
    }
}
