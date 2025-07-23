package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class DateFieldPage extends BasePage {

    private static final String ROUTE = RouteConfig.DATE_FIELD;

    private final Locator departureInput;
    private final Locator returnInput;

    public DateFieldPage(Page page) {
        super(page);
        departureInput = page.locator("dwc-field:has-text('Departure Date')");
        returnInput = page.locator("dwc-field:has-text('Return Date')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDepartureInput() {
        return departureInput;
    }

    public Locator getReturnInput() {
        return returnInput;
    }
}