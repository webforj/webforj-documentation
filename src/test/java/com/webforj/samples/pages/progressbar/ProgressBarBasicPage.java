package com.webforj.samples.pages.progressbar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ProgressBarBasicPage extends BasePage {

    private static final String ROUTE = "progressbarbasic";

    private final Locator progressBar;
    private final Locator startButton;
    private final Locator pauseButton;
    private final Locator resetButton;

    public ProgressBarBasicPage(Page page) {
        super(page);

        progressBar = page.locator("dwc-progressbar");
        startButton = page.locator("dwc-button:has-text('Start')");
        pauseButton = page.locator("dwc-button:has-text('Pause')");
        resetButton = page.locator("dwc-button:has-text('Reset')");
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