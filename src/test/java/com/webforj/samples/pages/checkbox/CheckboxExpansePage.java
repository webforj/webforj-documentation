package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CheckboxExpansePage {


    private static final String ROUTE = "checkboxexpanse";

    private final Locator checkboxXSmall;

    public CheckboxExpansePage(Page page) {
        this.checkboxXSmall = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("XSMALL"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCheckboxXSmall() {
        return checkboxXSmall;
    }
}
