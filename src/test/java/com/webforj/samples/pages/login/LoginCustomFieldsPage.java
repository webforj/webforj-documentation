package com.webforj.samples.pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class LoginCustomFieldsPage extends BasePage {

    private static final String ROUTE = "logincustomfields";

    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator errorMessage;
    private final Locator logoutButton;
    private final Locator customerID;
    private final Locator errorHost;

    public LoginCustomFieldsPage(Page page) {
        super(page);

        Locator loginHost = page.locator("dwc-login");
        this.errorHost = loginHost.locator("dwc-alert[part='error'][opened]");

        this.customerID = page.locator("dwc-field[slot='before-form']").locator("[part~='input']");
        this.username = loginHost.locator("#username").locator("[part~='input']");
        this.password = loginHost.locator("#password").locator("[part~='input']");
        this.signInButton = loginHost.locator("[part='submit-button']").locator("[part~='control']");
        this.errorMessage = errorHost.locator("[part~='control'][role='alert']");
        this.logoutButton = page.locator("dwc-button").filter(new Locator.FilterOptions().setHasText("Logout"))
                .locator("[part~='control']");
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

    public Locator getErrorMessage() {
        return errorMessage;
    }

    public Locator getLogoutButton() {
        return logoutButton;
    }

    public Locator getCustomderID() {
        return customerID;
    }

    public Locator getErrorHost() {
        return errorHost;
    }
}