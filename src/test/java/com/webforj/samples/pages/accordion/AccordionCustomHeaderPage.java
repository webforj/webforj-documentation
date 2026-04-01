package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AccordionCustomHeaderPage {

    private static final String ROUTE = "accordioncustomheader";

    private final Locator customHeaderPanel;
    private final Locator userSettingsPanel;

    public AccordionCustomHeaderPage(Page page) {
        this.customHeaderPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Custom Header with Icon"));
        this.userSettingsPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("User Settings"));
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getCustomHeaderPanel() {
        return customHeaderPanel;
    }

    public Locator getUserSettingsPanel() {
        return userSettingsPanel;
    }
}
