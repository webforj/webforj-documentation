package com.webforj.samples.pages.flexlayout.container;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class FlexPositioningPage extends BasePage {

    private static final String ROUTE = "flexpositioning";

    private final Locator flexPositioningContainer;
    private final Locator flexPositioningDropdown;
    private final Locator listBox;

    public FlexPositioningPage(Page page) {
        super(page);

        flexPositioningContainer = page.locator(".button__container--single-row");
        Locator shadowRootDropdown = page.locator("dwc-button:has-text('Flex-start')");
        flexPositioningDropdown = shadowRootDropdown.locator("button");
        Locator shadowRootListBox = page.locator("dwc-listbox");
        listBox = shadowRootListBox.locator("li");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFlexPositioningContainer() {
        return flexPositioningContainer;
    }

    public Locator getFlexPositioningDropdown() {
        return flexPositioningDropdown;
    }

    public Locator getListBox(String text) {
        return listBox.locator("text=\"" + text + "\"");
    }
}