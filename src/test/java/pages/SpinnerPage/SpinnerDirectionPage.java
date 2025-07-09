package pages.SpinnerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SpinnerDirectionPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPINNER_DIRECTION_DEMO;

    private final Locator spinner;
    private final Locator clockwiseButton;
    private final Locator counterClockwiseButton;

    public SpinnerDirectionPage(Page page) {
        super(page);

        spinner = page.locator("dwc-spinner.hydrated");
        clockwiseButton = page.locator("dwc-button:has-text('Clockwise') >> button").first();
        counterClockwiseButton = page.locator("dwc-button:has-text('Counterclockwise') >> button").first();
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