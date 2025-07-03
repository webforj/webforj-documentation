package pages.AlertPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class AlertExpansesPage extends BasePage {

    private static final String ROUTE = RouteConfig.ALERT_EXPANSES;

    private final Locator noneExpanseAlert;
    private final Locator xsmallExpanseAlert;
    private final Locator smallExpanseAlert;
    private final Locator mediumExpanseAlert;
    private final Locator largeExpanseAlert;
    private final Locator xlargeExpanseAlert;

    public AlertExpansesPage(Page page) {
        super(page);

        noneExpanseAlert = page.locator("dwc-alert[expanse='']");
        xsmallExpanseAlert = page.locator("dwc-alert[expanse='xs']");
        smallExpanseAlert = page.locator("dwc-alert[expanse='s']");
        mediumExpanseAlert = page.locator("dwc-alert[expanse='m']");
        largeExpanseAlert = page.locator("dwc-alert[expanse='l']");
        xlargeExpanseAlert = page.locator("dwc-alert[expanse='xl']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNoneExpanseAlert() {
        return noneExpanseAlert;
    }

    public Locator getXsmallExpanseAlert() {
        return xsmallExpanseAlert;
    }

    public Locator getSmallExpanseAlert() {
        return smallExpanseAlert;
    }

    public Locator getMediumExpanseAlert() {
        return mediumExpanseAlert;
    }

    public Locator getLargeExpanseAlert() {
        return largeExpanseAlert;
    }

    public Locator getXlargeExpanseAlert() {
        return xlargeExpanseAlert;
    }

}