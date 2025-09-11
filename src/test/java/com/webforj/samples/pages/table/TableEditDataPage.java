package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TableEditDataPage {
    private static final String ROUTE = "tableeditdata";

    private final Page page;
    private final Locator editButton;
    private final Locator input;
    private final Locator saveButton;

    public TableEditDataPage(Page page) {
        this.page = page;
        
        this.editButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("pencil pin")).nth(0);
        this.input = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("New Title"));
        this.saveButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));

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

    public Locator verifyTitle(String title){
        return page.getByRole(AriaRole.CELL,
    new Page.GetByRoleOptions().setName(title));
    }

}