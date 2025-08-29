package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class TableColumnPinningPage extends BasePage {
    private static final String ROUTE = "tablecolumnpinning";

    private final Locator editButton;
    private final Locator dialogBox;

    public TableColumnPinningPage(Page page) {
        super(page);

        this.editButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Edit").setExact(true)).nth(0);
        this.dialogBox = page.getByText("You asked to edit record number ", new Page.GetByTextOptions().setExact(false));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getEditButton() {
        return editButton;
    }

    public Locator getDialogBox() {
        return dialogBox;
    }
}