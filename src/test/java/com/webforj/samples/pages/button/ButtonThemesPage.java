package com.webforj.samples.pages.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ButtonThemesPage {

    private static final String ROUTE = "buttonthemes";

    private final Locator solidButton;
    private final Locator outlinedButton;

    public ButtonThemesPage(Page page) {
        this.solidButton = page.locator("dwc-button").filter(new Locator.FilterOptions().setHasText("Danger"));
        this.outlinedButton = page.locator("dwc-button").filter(new Locator.FilterOptions().setHasText("Outlined Button"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSolidButton() {
        return solidButton;
    }

    public Locator getOutlinedButton() {
        return outlinedButton;
    }

}
