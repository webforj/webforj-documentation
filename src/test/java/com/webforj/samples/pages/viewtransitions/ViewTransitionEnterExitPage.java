package com.webforj.samples.pages.viewtransitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ViewTransitionEnterExitPage {

    private static final String ROUTE = "viewtransitionenterexit/";

    private final Locator triggerBtn;
    private final Locator notificationCard;
    private final Locator notificationDismiss;
    private final Locator notificationTitle;
    private final Locator notificationMessage;

    public ViewTransitionEnterExitPage(Page page) {
        this.triggerBtn = page.locator(".trigger-btn");
        this.notificationCard = page.locator(".notification-card");
        this.notificationDismiss = page.locator(".notification-dismiss");
        this.notificationTitle = page.locator(".notification-title");
        this.notificationMessage = page.locator(".notification-message");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTriggerBtn() {
        return triggerBtn;
    }

    public Locator getNotificationCard() {
        return notificationCard;
    }

    public Locator getNotificationDismiss() {
        return notificationDismiss;
    }

    public Locator getNotificationTitle() {
        return notificationTitle;
    }

    public Locator getNotificationMessage() {
        return notificationMessage;
    }
}
