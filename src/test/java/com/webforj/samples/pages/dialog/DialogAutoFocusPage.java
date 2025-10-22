package com.webforj.samples.pages.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DialogAutoFocusPage {

    private static final String ROUTE = "dialogautofocus";

    private final Locator textField;

    public DialogAutoFocusPage(Page page) {
        this.textField = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("This Box is Auto Focused"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTextField() {
        return textField;
    }
}
