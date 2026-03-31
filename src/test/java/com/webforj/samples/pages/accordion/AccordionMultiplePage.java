package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AccordionMultiplePage {

    private static final String ROUTE = "accordionmultiple";

    private final Locator panelA;
    private final Locator panelB;
    private final Locator panelC;
    private final Locator openAllButton;
    private final Locator closeAllButton;

    public AccordionMultiplePage(Page page) {
        this.panelA = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Panel A"));
        this.panelB = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Panel B"));
        this.panelC = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Panel C"));
        this.openAllButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open All"));
        this.closeAllButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Close All"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPanelA() {
        return panelA;
    }

    public Locator getPanelB() {
        return panelB;
    }

    public Locator getPanelC() {
        return panelC;
    }

    public Locator getOpenAllButton() {
        return openAllButton;
    }

    public Locator getCloseAllButton() {
        return closeAllButton;
    }
}
