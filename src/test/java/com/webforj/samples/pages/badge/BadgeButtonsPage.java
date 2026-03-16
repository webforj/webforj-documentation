package com.webforj.samples.pages.badge;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BadgeButtonsPage {

    private static final String ROUTE = "badgebuttons";

    private final Locator notificationsButton;
    private final Locator messagesButton;

    public BadgeButtonsPage(Page page) {
        this.notificationsButton = page.locator("dwc-button").filter(
                new Locator.FilterOptions().setHasText("Notifications"));
        this.messagesButton = page.locator("dwc-button").filter(
                new Locator.FilterOptions().setHasText("Messages"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNotificationsButton() {
        return notificationsButton;
    }

    public Locator getMessagesButton() {
        return messagesButton;
    }
}
