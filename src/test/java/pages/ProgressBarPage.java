package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProgressBarPage extends BasePage {

    private final Locator progressBar;
    private final Locator startButton;
    private final Locator pauseButton;
    private final Locator resetButton;

    private final Locator determinateProgressBar;

    private final Locator dangerBar;
    private final Locator defaultBar;
    private final Locator grayBar;
    private final Locator infoBar;
    private final Locator primaryBar;
    private final Locator successBar;
    private final Locator warningBar;

    public ProgressBarPage(Page page) {
        super(page);

        // Basics
        progressBar = page.locator("dwc-progressbar[dwc-id='19']");
        startButton = page.locator("dwc-button[dwc-id='13']");
        pauseButton = page.locator("dwc-button[dwc-id='15']");
        resetButton = page.locator("dwc-button[dwc-id='17']");

        // Determinate
        determinateProgressBar = page.locator("dwc-progressbar[dwc-id='11']");

        // Themes
        dangerBar = page.locator("dwc-progressbar[dwc-id='12']");
        defaultBar = page.locator("dwc-progressbar[dwc-id='13']");
        grayBar = page.locator("dwc-progressbar[dwc-id='14']");
        infoBar = page.locator("dwc-progressbar[dwc-id='15']");
        primaryBar = page.locator("dwc-progressbar[dwc-id='16']");
        successBar = page.locator("dwc-progressbar[dwc-id='17']");
        warningBar = page.locator("dwc-progressbar[dwc-id='18']");
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

    public Locator getDeterminateProgressBar() {
        return determinateProgressBar;
    }

    public Locator getDangerBar() {
        return dangerBar;
    }

    public Locator getDefaultBar() {
        return defaultBar;
    }

    public Locator getGrayBar() {
        return grayBar;
    }

    public Locator getInfoBar() {
        return infoBar;
    }

    public Locator getPrimaryBar() {
        return primaryBar;
    }

    public Locator getSuccessBar() {
        return successBar;
    }

    public Locator getWarningBar() {
        return warningBar;
    }
}