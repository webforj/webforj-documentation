package com.webforj.samples.pages.spinner;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SpinnerDirectionDemoPage extends BasePage {

    private static final String ROUTE = "spinnerdirectiondemo";

    private final Locator spinner;
    private final Locator clockwiseButton;
    private final Locator counterClockwiseButton;

    public SpinnerDirectionDemoPage(Page page) {
        super(page);

        this.spinner = page.locator("dwc-spinner");
        this.clockwiseButton = page.locator("dwc-button:has-text('Clockwise')").locator("button").first();
        this.counterClockwiseButton = page.locator("dwc-button:has-text('Counterclockwise')").locator("button").first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSpinner() {
        return spinner;
    }

    public Locator getClockwiseButton() {
        return clockwiseButton;
    }

    public Locator getCounterClockwiseButton() {
        return counterClockwiseButton;
    }
}