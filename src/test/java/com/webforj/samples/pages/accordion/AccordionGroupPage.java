package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccordionGroupPage {

    private static final String ROUTE = "accordiongroup";

    private final Locator panelWebforJ;
    private final Locator panelGrouped;
    private final Locator panelMultiple;

    public AccordionGroupPage(Page page) {
        this.panelWebforJ = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("What is webforJ?"));
        this.panelGrouped = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("How do grouped panels work?"));
        this.panelMultiple = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Can I have multiple groups?"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPanelWebforJ() {
        return panelWebforJ;
    }

    public Locator getPanelGrouped() {
        return panelGrouped;
    }

    public Locator getPanelMultiple() {
        return panelMultiple;
    }
}
