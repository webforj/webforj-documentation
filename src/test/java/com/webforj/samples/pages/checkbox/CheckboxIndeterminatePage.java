package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CheckboxIndeterminatePage {

    private static final String ROUTE = "checkboxindeterminate";

    private final Locator parentCheckbox;
    private final Locator child1Checkbox;
    private final Locator child2Checkbox;

    public CheckboxIndeterminatePage(Page page) {
        this.parentCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Parent"));
        this.child1Checkbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Child 1"));
        this.child2Checkbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Child 2"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getParentCheckbox() {
        return parentCheckbox;
    }

    public Locator getChild1Checkbox() {
        return child1Checkbox;
    }

    public Locator getChild2Checkbox() {
        return child2Checkbox;
    }

}
