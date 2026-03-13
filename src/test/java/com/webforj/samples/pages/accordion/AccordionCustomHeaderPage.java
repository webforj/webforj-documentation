package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccordionCustomHeaderPage {

    private static final String ROUTE = "webforj/accordion/customheader";

    private final Locator customHeaderPanel;
    private final Locator userSettingsPanel;

    public AccordionCustomHeaderPage(Page page) {
        this.customHeaderPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Custom Header with Icon"));
        this.userSettingsPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("User Settings"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCustomHeaderPanel() {
        return customHeaderPanel;
    }

    public Locator getUserSettingsPanel() {
        return userSettingsPanel;
    }
}
