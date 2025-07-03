package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class DateTimeFieldPage extends BasePage {

    private static final String ROUTE = RouteConfig.DATE_TIME_FIELD;

    private final Locator departureInput;

    public DateTimeFieldPage(Page page) {
        super(page);
        departureInput = page.locator("dwc-field:has-text('Departure Date')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDepartureInput() {
        return departureInput;
    }
}