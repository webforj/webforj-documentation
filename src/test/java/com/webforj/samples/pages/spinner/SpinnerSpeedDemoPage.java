package com.webforj.samples.pages.spinner;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class SpinnerSpeedDemoPage extends BasePage {

    private static final String ROUTE = "spinnerspeeddemo";

    private final Locator spinner;
    private final Locator slowButton;
    private final Locator mediumButton;
    private final Locator fastButton;
    private final Locator pauseButton;

    public SpinnerSpeedDemoPage(Page page) {
        super(page);

        this.spinner = page.locator("dwc-spinner");
        this.slowButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Slow"));
        this.mediumButton =page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Medium"));
        this.fastButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Fast"));
        this.pauseButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Pause"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSpinner() {
        return spinner;
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