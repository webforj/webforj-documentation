package pages.SpinnerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SpinnerDirectionPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPINNER_DIRECTION_DEMO;

    private final Locator spinner11;
    private final Locator clockwiseButton;
    private final Locator counterClockwiseButton;

    public SpinnerDirectionPage(Page page) {
        super(page);

        spinner11 = page.locator("dwc-spinner[dwc-id='11']");
        clockwiseButton = page.locator("dwc-button[dwc-id='13']");
        counterClockwiseButton = page.locator("dwc-button[dwc-id='14']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSpinner11() {
        return spinner11;
    }

    public Locator getClockwiseButton() {
        return clockwiseButton;
    }

    public Locator getCounterClockwiseButton() {
        return counterClockwiseButton;
    }
}