package com.webforj.samples.pages.fields.datefield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DateFieldPage {

    private static final String ROUTE = "datefield";

    private final Locator departureDate;
    private final Locator returnDate;

    public DateFieldPage(Page page) {
        
        this.departureDate = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Departure Date"));
        this.returnDate = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Return Date"));
        
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDepartureDate() {
        return departureDate;
    }

    public Locator getReturnDate() {
        return returnDate;
    }
}