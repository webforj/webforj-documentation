package com.webforj.samples.pages.fields.datefield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class DateFieldPage extends BasePage {

    private static final String ROUTE = "datefield";

    private final Locator departureDate;
    private final Locator returnDate;

    public DateFieldPage(Page page) {
        super(page);
        
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