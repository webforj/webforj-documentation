package com.webforj.samples.pages.fields.datefield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class DateFieldPage extends BasePage {

    private static final String ROUTE = "datefield";

    private final Locator departureInput;
    private final Locator returnInput;
    private static Locator departureInputHost;
    private static Locator returnInputHost;

    public DateFieldPage(Page page) {
        super(page);

        departureInputHost = page.locator("dwc-field[type='date']")
                .filter(new Locator.FilterOptions().setHas(page.locator("label:has-text('Departure Date')")));
        departureInput = departureInputHost.locator("[part~='input']:visible").first();
        returnInputHost = page.locator("dwc-field[type='date']")
                .filter(new Locator.FilterOptions().setHas(page.locator("label:has-text('Return Date')")));
        returnInput = returnInputHost.locator("[part~='input']:visible").first();
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

    public Locator getDepartureInputHost() {
        return departureInputHost;
    }

    public Locator getReturnInputHost() {
        return returnInputHost;
    }
}