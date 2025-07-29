package pages.spinner;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class SpinnerDirectionDemoPage extends BasePage {

    private static final String ROUTE = "spinnerdirectiondemo";

    private final Locator spinner;
    private final Locator clockwiseButton;
    private final Locator counterClockwiseButton;

    public SpinnerDirectionDemoPage(Page page) {
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