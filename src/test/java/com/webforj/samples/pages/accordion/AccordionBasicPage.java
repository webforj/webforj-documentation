package com.webforj.samples.pages.accordion;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AccordionBasicPage {

    private static final String ROUTE = "accordionbasic";

    private final Locator sectionOne;
    private final Locator sectionTwo;
    private final Locator sectionThree;

    public AccordionBasicPage(Page page) {
        this.sectionOne = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Section One"));
        this.sectionTwo = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Section Two"));
        this.sectionThree = page.locator("dwc-accordion-panel").filter(
                new Locator.FilterOptions().setHasText("Section Three"));
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getSectionOne() {
        return sectionOne;
    }

    public Locator getSectionTwo() {
        return sectionTwo;
    }

    public Locator getSectionThree() {
        return sectionThree;
    }
}
