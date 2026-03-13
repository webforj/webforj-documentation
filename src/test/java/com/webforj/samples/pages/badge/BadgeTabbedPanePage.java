package com.webforj.samples.pages.badge;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BadgeTabbedPanePage {

    private static final String ROUTE = "badgetabbedpane";

    private final Locator inboxTab;
    private final Locator notificationsTab;
    private final Locator draftsTab;

    public BadgeTabbedPanePage(Page page) {
        this.inboxTab = page.locator("dwc-tab").filter(
                new Locator.FilterOptions().setHasText("Inbox")).first();
        this.notificationsTab = page.locator("dwc-tab").filter(
                new Locator.FilterOptions().setHasText("Notifications")).first();
        this.draftsTab = page.locator("dwc-tab").filter(
                new Locator.FilterOptions().setHasText("Drafts")).first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInboxTab() {
        return inboxTab;
    }

    public Locator getNotificationsTab() {
        return notificationsTab;
    }

    public Locator getDraftsTab() {
        return draftsTab;
    }
}
