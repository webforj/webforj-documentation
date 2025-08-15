package com.webforj.samples.pages.tabbedpane;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class TabbedPanePlacementPage extends BasePage {

    private static final String ROUTE = "tabbedpaneplacement";

    private final Locator placementDropdown;
    private final Locator placementListBox;
    private final Locator placementTabbedPane;

    public TabbedPanePlacementPage(Page page) {
        super(page);

        this.placementDropdown = page.locator("dwc-choicebox").locator("dwc-dropdown");
        this.placementListBox = page.locator("dwc-listbox").locator("ul");
        this.placementTabbedPane = page.locator("dwc-tabbed-pane");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPlacementDropdown() {
        return placementDropdown;
    }

    public Locator getPlacementListBox() {
        return placementListBox;
    }

    public Locator getPlacementTabbedPane() {
        return placementTabbedPane;
    }
}