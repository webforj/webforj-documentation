package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AlertExpansesPage {

    private static final String ROUTE = "alertexpanses";

    private final Locator alertXSmall;
    private final Locator alertMedium;
    private final Locator alertXLarge;

    public AlertExpansesPage(Page page) {
        this.alertXSmall = page.locator("dwc-alert").filter(new Locator.FilterOptions().setHasText("This alert uses the XSMALL expanse."));
        this.alertMedium = page.locator("dwc-alert").filter(new Locator.FilterOptions().setHasText("This alert uses the MEDIUM expanse."));
        this.alertXLarge = page.locator("dwc-alert").filter(new Locator.FilterOptions().setHasText("This alert uses the XLARGE expanse."));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getAlertXSmall() {
        return alertXSmall;
    }

    public Locator getAlertMedium() {
        return alertMedium;
    }

    public Locator getAlertXLarge() {
        return alertXLarge;
    }
}
