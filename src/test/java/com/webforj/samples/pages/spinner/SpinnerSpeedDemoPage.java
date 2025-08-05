package com.webforj.samples.pages.spinner;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SpinnerSpeedDemoPage extends BasePage {

    private static final String ROUTE = "spinnerspeeddemo";

    private final Locator spinner11;
    private final Locator slowButton;
    private final Locator mediumButton;
    private final Locator fastButton;
    private final Locator pauseButton;

    public SpinnerSpeedDemoPage(Page page) {
        super(page);

        spinner11 = page.locator("dwc-spinner[theme='primary']");
        slowButton = page.locator("dwc-button:has-text('Slow')");
        mediumButton = page.locator("dwc-button:has-text('Medium')");
        fastButton = page.locator("dwc-button:has-text('Fast')");
        pauseButton = page.locator("dwc-button:has-text('Pause')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSpinner11() {
        return spinner11;
    }

    public Locator getSlowButton() {
        return slowButton;
    }

    public Locator getMediumButton() {
        return mediumButton;
    }

    public Locator getFastButton() {
        return fastButton;
    }

    public Locator getPauseButton() {
        return pauseButton;
    }
}