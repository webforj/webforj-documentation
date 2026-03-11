package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccordionEventsPage {

    private static final String ROUTE = "events";

    private final Locator panel;
    private final Locator toast;

    public AccordionEventsPage(Page page) {
        this.panel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Click me to see events"));
        this.toast = page.locator("dwc-toast");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPanel() {
        return panel;
    }

    public Locator getToast() {
        return toast;
    }
}
