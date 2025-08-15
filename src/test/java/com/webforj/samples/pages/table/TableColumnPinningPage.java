package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableColumnPinningPage extends BasePage {
    private static final String ROUTE = "tablecolumnpinning";

    private final Locator editButtonPosition;
    private final Locator editButton;
    private final Locator dialogBox;
    private final Locator dataTableHost;

    public TableColumnPinningPage(Page page) {
        super(page);

        this.dataTableHost = page.locator("dwc-table");

        this.editButtonPosition = dataTableHost.locator("td[part~='cell-pinned-right']").first();
        this.editButton = dataTableHost.locator("td dwc-button").first();
        this.dialogBox = page.locator("dwc-dialog[type='msgbox']").locator("section");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getEditButtonPosition() {
        return editButtonPosition;
    }

    public Locator getEditButton() {
        return editButton;
    }

    public Locator getDialogBox() {
        return dialogBox;
    }
}