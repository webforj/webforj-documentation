package com.webforj.samples.pages.drawer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DrawerAutoFocusPage {

    private static final String ROUTE = "drawerautofocus";

    private final Locator emailNotifications;

    public DrawerAutoFocusPage(Page page) {
        this.emailNotifications = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Email Notifications"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getEmailNotifications() {
        return emailNotifications;
    }

}
