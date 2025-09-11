package com.webforj.samples.pages.tabbedpane;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TabbedPaneAlignmentPage {

    private static final String ROUTE = "tabbedpanealignment";

    private final Locator alignmentDropdown;
    private final Locator alignmentDropdownButton;
    private final Locator alignmentTabbedPane;

    public TabbedPaneAlignmentPage(Page page) {

        this.alignmentDropdown = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("AUTO"));
        this.alignmentDropdownButton = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("CENTER"));
        this.alignmentTabbedPane = page.locator("dwc-tabbed-pane");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getAlignmentDropdown() {
        return alignmentDropdown;
    }

    public Locator getAlignmentDropdownButton() {
        return alignmentDropdownButton;
    }

    public Locator getAlignmentTabbedPane() {
        return alignmentTabbedPane;
    }
}