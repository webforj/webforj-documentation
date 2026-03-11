package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AccordionDisabledPage {

    private static final String ROUTE = "disabled";

    private final Locator disabledPanel;
    private final Locator disabledOpenedPanel;
    private final Locator toggleButton;
    // Accordion.setEnabled() delegates to child panels, so check a panel inside
    // the group rather than the dwc-accordion container itself.
    private final Locator groupPanelOne;

    public AccordionDisabledPage(Page page) {
        this.disabledPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("This panel is disabled"));
        this.disabledOpenedPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Disabled but opened"));
        this.toggleButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Toggle Enabled"));
        this.groupPanelOne = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Panel One"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDisabledPanel() {
        return disabledPanel;
    }

    public Locator getDisabledOpenedPanel() {
        return disabledOpenedPanel;
    }

    public Locator getToggleButton() {
        return toggleButton;
    }

    public Locator getGroupPanelOne() {
        return groupPanelOne;
    }
}
