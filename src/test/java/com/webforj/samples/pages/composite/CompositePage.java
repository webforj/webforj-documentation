package com.webforj.samples.pages.composite;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

    public class CompositePage {

    private static final String ROUTE = "composite/";

    private final Locator toDoInput;
    private final Locator firstTaskCheckbox;
    private final Locator firstDeleteButton;

    public CompositePage(Page page) {
        this.toDoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter a new task and press Enter..."));
        this.firstTaskCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Review documentation"));
        this.firstDeleteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete")).first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getToDoInput() {
        return toDoInput;
    }

    public Locator getFirstTaskCheckbox() {
        return firstTaskCheckbox;
    }

    public Locator getFirstDeleteButton() {
        return firstDeleteButton;
    }
}
