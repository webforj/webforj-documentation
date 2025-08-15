package com.webforj.samples.pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class LoginCancelButtonPage extends BasePage {

    private static final String ROUTE = "logincancelbutton";

    private final Locator cancelButton;
    private final Locator signInButton;

    public LoginCancelButtonPage(Page page) {
        super(page);

        Locator shadowRoot = page.locator("dwc-login");
        cancelButton = shadowRoot.locator("dwc-button[part='cancel-button']").locator("button");
        signInButton = shadowRoot.locator("dwc-button[part='submit-button']").locator("button");
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