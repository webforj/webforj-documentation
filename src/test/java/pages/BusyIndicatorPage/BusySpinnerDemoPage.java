package pages.BusyIndicatorPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;
import com.webforj.samples.config.RouteConfig;

public class BusySpinnerDemoPage extends BasePage {

    private static final String ROUTE = RouteConfig.BUSY_SPINNERS;

    private final Locator busySpinner;

    public BusySpinnerDemoPage(Page page) {
        super(page);

        busySpinner = page.locator("dwc-loading");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getBusySpinner() {
        return busySpinner;
    }
}