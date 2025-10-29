package com.webforj.samples.pages.columnslayout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ColumnsLayoutAlignmentPage {

    private static final String ROUTE = "columnslayoutalignment";

    private final Locator firstName;
    private final Locator lastName;

    public ColumnsLayoutAlignmentPage(Page page) {
        this.firstName = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name"));
        this.lastName = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFirstName() {
        return firstName;
    }

    public Locator getLastName() {
        return lastName;
    }
}
