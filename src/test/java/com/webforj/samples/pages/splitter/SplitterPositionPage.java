package com.webforj.samples.pages.splitter;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SplitterPositionPage extends BasePage {

    private static final String ROUTE = "splitterposition";

    private final Locator positionedMasterPanel;
    private final Locator positionedDetailPanel;
    private final Locator shadowRootSplitter;

    public SplitterPositionPage(Page page) {
        super(page);

        this.shadowRootSplitter = page.locator("dwc-splitter");
        this.positionedMasterPanel = shadowRootSplitter.locator(".splitter-box--info");
        this.positionedDetailPanel = shadowRootSplitter.locator(".splitter-box--success");
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