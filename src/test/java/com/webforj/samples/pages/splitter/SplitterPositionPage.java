package com.webforj.samples.pages.splitter;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SplitterPositionPage extends BasePage {

    private static final String ROUTE = "splitterposition";

    private final Locator positionedMasterPanel;
    private final Locator positionedDetailPanel;

    public SplitterPositionPage(Page page) {
        super(page);

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