package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccordionSlotsPage {

    private static final String ROUTE = "slots";

    private final Locator customHeaderPanel;
    private final Locator userSettingsPanel;
    private final Locator customIconPanel;

    public AccordionSlotsPage(Page page) {
        this.customHeaderPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Custom Header with Icon"));
        this.userSettingsPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("User Settings"));
        this.customIconPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Plus Icon Panel"));
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

    public Locator getCustomIconPanel() {
        return customIconPanel;
    }
}
