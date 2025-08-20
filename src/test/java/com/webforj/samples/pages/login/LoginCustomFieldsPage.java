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
    private final Locator errorHost;

    public LoginCustomFieldsPage(Page page) {
        super(page);

        Locator loginHost = page.locator("dwc-login");
        customerID = loginHost.locator("dwc-field")
                .filter(new Locator.FilterOptions().setHas(page.locator("label:has-text('Customer ID')")))
                .locator("[part~='input']:visible").first();

        username = loginHost.locator("dwc-field")
                .filter(new Locator.FilterOptions().setHas(page.locator("label:has-text('Username')")))
                .locator("[part~='input']:visible").first();

        password = loginHost.locator("dwc-field")
                .filter(new Locator.FilterOptions().setHas(page.locator("label:has-text('Password')")))
                .locator("[part~='input']:visible").first();

        signInButton = loginHost.locator("dwc-button")
                .filter(new Locator.FilterOptions().setHasText("Sign In"))
                .locator("[part~='control']");

        errorHost = loginHost.locator("dwc-dialog[opened]").getByRole(AriaRole.ALERT);

        logoutButton = page.locator("dwc-button")
                .filter(new Locator.FilterOptions().setHasText("Logout"))
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