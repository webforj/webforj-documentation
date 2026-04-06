package com.webforj.samples.pages.fields.timefield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TimeFieldPage {

    private static final String ROUTE = "timefield";

    private final Locator reminder;

    public TimeFieldPage(Page page) {
        this.reminder = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Set Reminder:").setExact(true));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getReminder() {
        return reminder;
    }

}
