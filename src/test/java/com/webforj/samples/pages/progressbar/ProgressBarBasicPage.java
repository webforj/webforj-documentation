package com.webforj.samples.pages.progressbar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class ProgressBarBasicPage extends BasePage {

    private static final String ROUTE = "progressbarbasic";

    private final Locator progressBar;
    private final Locator startButton;
    private final Locator pauseButton;
    private final Locator resetButton;

    public ProgressBarBasicPage(Page page) {
        super(page);

        this.progressBar = page.locator("dwc-progressbar");
        this.startButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Start"));
        this.pauseButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Pause"));
        this.resetButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getProgressBar() {
        return progressBar;
    }

    public Locator getStartButton() {
        return startButton;
    }

    public Locator getPauseButton() {
        return pauseButton;
    }

    public Locator getResetButton() {
        return resetButton;
    }
}