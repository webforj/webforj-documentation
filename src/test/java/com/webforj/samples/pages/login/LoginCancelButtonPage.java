package com.webforj.samples.pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginCancelButtonPage {

    private static final String ROUTE = "logincancelbutton";

    private final Locator cancelButton;
    private final Locator signInButton;

    public LoginCancelButtonPage(Page page) {
        
        cancelButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel"));
        signInButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCancelButton() {
        return cancelButton;
    }

    public Locator getSignInButton() {
        return signInButton;
    }
}