package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableEditDataPage extends BasePage {
    private static final String ROUTE = "tableeditdata";

    private final Locator editButton;
    private final Locator input;
    private final Locator saveButton;
    private final Locator title;
    private final Locator dataTableHost;
    private final Locator dialogHost;

    public TableEditDataPage(Page page) {
        super(page);

        this.dataTableHost = page.locator("dwc-table");
        this.dialogHost = page.locator("dwc-dialog");

        this.editButton = dataTableHost.locator("dwc-icon-button").first().locator("button");
        this.input = dialogHost.locator("dwc-field").locator("#field-1");
        this.saveButton = dialogHost.locator("dwc-button:has-text('Save')").locator("button");
        this.title = dataTableHost.locator("tr td").nth(1).first();

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getEditButton() {
        return editButton;
    }

    public Locator getInput() {
        return input;
    }

    public Locator getSaveButton() {
        return saveButton;
    }

    public Locator getTitle() {
        return title;
    }

}