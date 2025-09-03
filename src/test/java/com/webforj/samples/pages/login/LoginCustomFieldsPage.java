package com.webforj.samples.pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class LoginCustomFieldsPage extends BasePage {

    private static final String ROUTE = "logincustomfields";

    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator logoutButton;
    private final Locator customerID;

    public LoginCustomFieldsPage(Page page) {
        super(page);

        customerID = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Customer ID •"));
        username = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username •"));
        password = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password •"));

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