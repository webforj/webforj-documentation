package com.webforj.samples.pages.tabbedpane;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;


public class TabbedPaneAlignmentPage extends BasePage {

    private static final String ROUTE = "tabbedpanealignment";

    private final Locator alignmentDropdown;
    private final Locator alignmentListBox;
    private final Locator alignmentTabbedPane;

    public TabbedPaneAlignmentPage(Page page) {
        super(page);

        this.alignmentDropdown = page.locator("dwc-choicebox").locator("dwc-dropdown");
        this.alignmentListBox = page.locator("dwc-listbox");
        this.alignmentTabbedPane = page.locator("dwc-tabbed-pane");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getAlignmentDropdown() {
        return alignmentDropdown;
    }

    public Locator getAlignmentListBox() {
        return alignmentListBox;
    }

    public Locator getAlignmentTabbedPane() {
        return alignmentTabbedPane;
    }
}