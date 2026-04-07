package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AccordionCustomIconPage {

    private static final String ROUTE = "accordioncustomicon";

    private final Locator customIconPanel;

    public AccordionCustomIconPage(Page page) {
        this.customIconPanel = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Plus Icon Panel"));
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getCustomIconPanel() {
        return customIconPanel;
    }
}
