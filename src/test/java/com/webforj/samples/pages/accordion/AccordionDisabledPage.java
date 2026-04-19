package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AccordionDisabledPage {
    private static final String ROUTE = "accordiondisabled";
    private final Locator disabledPanel;
    private final Locator disabledOpenedPanel;
    private final Locator toggleButton;
    private final Locator groupPanelOne;

    public AccordionDisabledPage(Page page) {
        this.disabledPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("This panel is disabled"));
        this.disabledOpenedPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Disabled but opened"));
        this.toggleButton = page.locator("dwc-radio");
        this.groupPanelOne = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Panel One"));
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
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