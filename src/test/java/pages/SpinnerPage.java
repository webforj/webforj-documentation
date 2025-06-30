package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SpinnerPage extends BasePage {

    // SpinnerBasics
    private final Locator checkIcon1;
    private final Locator checkIcon2;
    private final Locator basicsSpinner;

    // SpinnerSpeeds & SpinnerDirection (shared spinner)
    private final Locator spinner11;
    private final Locator slowButton;
    private final Locator mediumButton;
    private final Locator fastButton;
    private final Locator pauseButton;
    private final Locator clockwiseButton;
    private final Locator counterClockwiseButton;

    // SpinnerThemes
    private final Locator defaultThemeSpinner;
    private final Locator primaryThemeSpinner;
    private final Locator successThemeSpinner;
    private final Locator dangerThemeSpinner;
    private final Locator warningThemeSpinner;
    private final Locator grayThemeSpinner;
    private final Locator infoThemeSpinner;

    // SpinnerExpanses
    private final Locator smallSpinner;
    private final Locator mediumSpinner;
    private final Locator largeSpinner;

    public SpinnerPage(Page page) {
        super(page);

        // SpinnerBasics
        checkIcon1 = page.locator("dwc-icon[dwc-id='14']");
        checkIcon2 = page.locator("dwc-icon[dwc-id='17']");
        basicsSpinner = page.locator("dwc-spinner[dwc-id='20']");

        // SpinnerSpeeds & SpinnerDirection
        spinner11 = page.locator("dwc-spinner[dwc-id='11']");
        slowButton = page.locator("dwc-button[dwc-id='13']");
        mediumButton = page.locator("dwc-button[dwc-id='14']");
        fastButton = page.locator("dwc-button[dwc-id='15']");
        pauseButton = page.locator("dwc-button[dwc-id='16']");
        clockwiseButton = page.locator("dwc-button[dwc-id='13']");
        counterClockwiseButton = page.locator("dwc-button[dwc-id='14']");

        // SpinnerThemes
        defaultThemeSpinner = page.locator("dwc-spinner[dwc-id='11']");
        primaryThemeSpinner = page.locator("dwc-spinner[dwc-id='12']");
        successThemeSpinner = page.locator("dwc-spinner[dwc-id='13']");
        dangerThemeSpinner = page.locator("dwc-spinner[dwc-id='14']");
        warningThemeSpinner = page.locator("dwc-spinner[dwc-id='15']");
        grayThemeSpinner = page.locator("dwc-spinner[dwc-id='16']");
        infoThemeSpinner = page.locator("dwc-spinner[dwc-id='17']");

        // SpinnerExpanses
        smallSpinner = page.locator("dwc-spinner[dwc-id='11']");
        mediumSpinner = page.locator("dwc-spinner[dwc-id='12']");
        largeSpinner = page.locator("dwc-spinner[dwc-id='13']");
    }

    // SpinnerBasics getters
    public Locator getCheckIcon1() {
        return checkIcon1;
    }

    public Locator getCheckIcon2() {
        return checkIcon2;
    }

    public Locator getBasicsSpinner() {
        return basicsSpinner;
    }

    // SpinnerSpeeds & SpinnerDirection getters
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

    public Locator getClockwiseButton() {
        return clockwiseButton;
    }

    public Locator getCounterClockwiseButton() {
        return counterClockwiseButton;
    }

    // SpinnerThemes getters
    public Locator getDefaultThemeSpinner() {
        return defaultThemeSpinner;
    }

    public Locator getPrimaryThemeSpinner() {
        return primaryThemeSpinner;
    }

    public Locator getSuccessThemeSpinner() {
        return successThemeSpinner;
    }

    public Locator getDangerThemeSpinner() {
        return dangerThemeSpinner;
    }

    public Locator getWarningThemeSpinner() {
        return warningThemeSpinner;
    }

    public Locator getGrayThemeSpinner() {
        return grayThemeSpinner;
    }

    public Locator getInfoThemeSpinner() {
        return infoThemeSpinner;
    }

    // SpinnerExpanses getters
    public Locator getSmallSpinner() {
        return smallSpinner;
    }

    public Locator getMediumSpinner() {
        return mediumSpinner;
    }

    public Locator getLargeSpinner() {
        return largeSpinner;
    }
}
