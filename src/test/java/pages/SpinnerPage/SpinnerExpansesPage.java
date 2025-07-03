package pages.SpinnerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SpinnerExpansesPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPINNER_EXPANSE_DEMO;

    private final Locator smallSpinner;
    private final Locator mediumSpinner;
    private final Locator largeSpinner;

    public SpinnerExpansesPage(Page page) {
        super(page);

        smallSpinner = page.locator("dwc-spinner[dwc-id='11']");
        mediumSpinner = page.locator("dwc-spinner[dwc-id='12']");
        largeSpinner = page.locator("dwc-spinner[dwc-id='13']");
    }

    public static String getRoute() {
        return ROUTE;
    }

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