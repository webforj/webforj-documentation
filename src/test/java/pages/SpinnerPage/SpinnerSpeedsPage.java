package pages.SpinnerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SpinnerSpeedsPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPINNER_SPEED_DEMO;

    private final Locator spinner11;
    private final Locator slowButton;
    private final Locator mediumButton;
    private final Locator fastButton;
    private final Locator pauseButton;

    public SpinnerSpeedsPage(Page page) {
        super(page);

        spinner11 = page.locator("dwc-spinner[dwc-id='11']");
        slowButton = page.locator("dwc-button[dwc-id='13']");
        mediumButton = page.locator("dwc-button[dwc-id='14']");
        fastButton = page.locator("dwc-button[dwc-id='15']");
        pauseButton = page.locator("dwc-button[dwc-id='16']");
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