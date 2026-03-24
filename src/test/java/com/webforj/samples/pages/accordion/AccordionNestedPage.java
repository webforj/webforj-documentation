package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccordionNestedPage {

    private static final String ROUTE = "webforj/accordion/nested";

    private final Locator outerPanel;
    private final Locator siblingPanel;
    private final Locator innerPanelA;
    private final Locator innerPanelB;

    public AccordionNestedPage(Page page) {
        // The outer panel's label is unique — filter by its full label text to
        // avoid matching the sibling panel whose body says "sibling of the outer panel".
        this.outerPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Outer Panel (contains nested accordion)"));
        this.siblingPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Sibling Panel"));
        // Inner panels are scoped to the dwc-accordion group to avoid matching the
        // outer panel, whose open subtree contains the inner panel label text.
        Locator innerAccordion = page.locator("dwc-accordion");
        this.innerPanelA = innerAccordion.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Inner Panel A"));
        this.innerPanelB = innerAccordion.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Inner Panel B"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getOuterPanel() {
        return outerPanel;
    }

    public Locator getSiblingPanel() {
        return siblingPanel;
    }

    public Locator getInnerPanelA() {
        return innerPanelA;
    }

    public Locator getInnerPanelB() {
        return innerPanelB;
    }
}
