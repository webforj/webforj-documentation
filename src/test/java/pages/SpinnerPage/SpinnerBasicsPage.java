package pages.SpinnerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SpinnerBasicsPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPINNER_DEMO;

    private final Locator checkIcon1;
    private final Locator checkIcon2;
    private final Locator basicsSpinner;

    public SpinnerBasicsPage(Page page) {
        super(page);

        checkIcon1 = page.locator("dwc-icon[dwc-id='14']");
        checkIcon2 = page.locator("dwc-icon[dwc-id='17']");
        basicsSpinner = page.locator("dwc-spinner[dwc-id='20']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCheckIcon1() {
        return checkIcon1;
    }

    public Locator getCheckIcon2() {
        return checkIcon2;
    }

    public Locator getBasicsSpinner() {
        return basicsSpinner;
    }
}