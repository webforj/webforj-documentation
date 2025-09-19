package com.webforj.samples.pages.splitter;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SplitterPositionPage {

    private static final String ROUTE = "splitterposition";

    private final Locator positionedMasterPanel;
    private final Locator positionedDetailPanel;

    public SplitterPositionPage(Page page) {

        this.positionedMasterPanel = page.getByText("Master");
        this.positionedDetailPanel = page.getByText("Detail");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPositionedMasterPanel() {
        return positionedMasterPanel;
    }

    public Locator getPositionedDetailPanel() {
        return positionedDetailPanel;
    }
}