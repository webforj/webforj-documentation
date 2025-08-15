package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableSingleSelectionPage extends BasePage {
    private static final String ROUTE = "tablesingleselection";

    private final Locator firstArtist;
    private final Locator tableHost;
    private final Locator dialogHost;
    private final Locator headerMessage;
    private final Locator dialogMessage;

    public TableSingleSelectionPage(Page page) {
        super(page);
        pageTitle = "Table Single Selection";

        this.tableHost = page.locator("dwc-table");
        this.dialogHost = page.locator("dwc-dialog");

        this.firstArtist = tableHost.getByText("Mississippi Blues");
        this.headerMessage = dialogHost.locator("header");
        this.dialogMessage = page.locator("section");

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFirstArtist() {
        return firstArtist;
    }

    public Locator getHeaderMessage() {
        return headerMessage;
    }

    public Locator getDialogMessage() {
        return dialogMessage;
    }
}