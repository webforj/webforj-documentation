package com.webforj.samples.pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginCustomFieldsPage {

    private static final String ROUTE = "logincustomfields";

    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator logoutButton;
    private final Locator customerID;

    public LoginCustomFieldsPage(Page page) {

        customerID = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Customer ID").setExact(false));
        username = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username").setExact(false));
        password = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password").setExact(false));

        signInButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in"));
        logoutButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Logout"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getUsername() {
        return username;
    }

    public Locator getPassword() {
        return password;
    }

    public Locator getSignInButton() {
        return signInButton;
    }

    public Locator getLogoutButton() {
        return logoutButton;
    }

    public Locator getCustomderID() {
        return customerID;
    }
}