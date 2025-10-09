package com.webforj.samples.pages.tabbedpane;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TabbedPanePlacementPage {

    private static final String ROUTE = "tabbedpaneplacement";

    private final Locator placementDropdown;
    private final Locator placementTabbedPane;
    private final Locator alignmentDropdownButton;

    public TabbedPanePlacementPage(Page page) {

        this.placementDropdown = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("TOP"));
        this.alignmentDropdownButton = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("BOTTOM"));
        this.placementTabbedPane = page.locator("dwc-tabbed-pane");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPlacementDropdown() {
        return placementDropdown;
    }

    public Locator getPlacementTabbedPane() {
        return placementTabbedPane;
    }

    public Locator getAlignmentDropdownButton() {
        return alignmentDropdownButton;
    }
}