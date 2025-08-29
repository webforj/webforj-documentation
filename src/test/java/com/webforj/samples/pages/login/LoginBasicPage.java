package com.webforj.samples.pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class LoginBasicPage extends BasePage {

    private static final String ROUTE = "loginbasic";

    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator dwcUsernameField;
    private final Locator dwcPasswordField;

    public LoginBasicPage(Page page) {
        super(page);

        Locator host = page.locator("dwc-login");
        Locator dialogHost = host.locator("dwc-dialog");

        username = page.locator("dwc-field")
                .filter(new Locator.FilterOptions().setHas(page.locator("label:has-text('Username')")))
                .locator("[part~='input']:visible").first();

        password = page.locator("dwc-field")
                .filter(new Locator.FilterOptions().setHas(page.locator("label:has-text('Password')")))
                .locator("[part~='input']:visible").first();

        signInButton = dialogHost.locator("dwc-button")
                .filter(new Locator.FilterOptions().setHasText("Sign in"))
                .locator("[part~='control']");

        dwcUsernameField = host.locator("#username");
        dwcPasswordField = host.locator("#password");
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

    public Locator getDwcUsernameField() {
        return dwcUsernameField;
    }

    public Locator getDwcPasswordField() {
        return dwcPasswordField;
    }
}