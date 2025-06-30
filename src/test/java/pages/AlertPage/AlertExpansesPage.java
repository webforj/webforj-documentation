package pages.AlertPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class AlertExpansesPage extends BasePage {

    private static final String ROUTE = "alertexpanses";

    private final Locator noneExpanseAlert;
    private final Locator xsmallExpanseAlert;
    private final Locator smallExpanseAlert;
    private final Locator mediumExpanseAlert;
    private final Locator largeExpanseAlert;
    private final Locator xlargeExpanseAlert;

    public AlertExpansesPage(Page page) {
        super(page);
        
        noneExpanseAlert = page.locator("dwc-alert[dwc-id='11']");
        xsmallExpanseAlert = page.locator("dwc-alert[dwc-id='15']");
        smallExpanseAlert = page.locator("dwc-alert[dwc-id='19']");
        mediumExpanseAlert = page.locator("dwc-alert[dwc-id='23']");
        largeExpanseAlert = page.locator("dwc-alert[dwc-id='27']");
        xlargeExpanseAlert = page.locator("dwc-alert[dwc-id='31']");
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