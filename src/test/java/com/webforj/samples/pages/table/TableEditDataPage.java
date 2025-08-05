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

    public TableEditDataPage(Page page) {
        super(page);

        editButton = page.locator("dwc-icon-button[name='pencil-pin'] >> button").first();
        input = page.locator("#field-1");
        saveButton = page.locator("dwc-button:has-text('Save')");
        title = page.locator("tr td").nth(1).first();

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