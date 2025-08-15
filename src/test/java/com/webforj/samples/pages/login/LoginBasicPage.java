package com.webforj.samples.pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class LoginBasicPage extends BasePage {

    private static final String ROUTE = "loginbasic";

    private final Locator header;
    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator passwordReveal;
    private final Locator dwcUsernameField;
    private final Locator dwcPasswordField;

    public LoginBasicPage(Page page) {
        super(page);

        Locator shadowRoot = page.locator("dwc-login");
        Locator shadowRootDialog = shadowRoot.locator("dwc-dialog");
        header = shadowRoot.locator("dwc-dialog > header");
        username = shadowRootDialog.locator("dwc-field#username").locator("input");
        password = shadowRootDialog.locator("dwc-field#password").locator("input");
        signInButton = shadowRootDialog.locator("dwc-button[part='submit-button']");
        passwordReveal = shadowRoot.locator("dwc-field#password").locator("dwc-icon-button[part='eye-off-icon']").locator("button");
        dwcUsernameField = shadowRoot.locator("#username");
        dwcPasswordField = shadowRoot.locator("#password");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHeader() {
        return header;
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

    public Locator getPasswordReveal() {
        return passwordReveal;
    }

    public Locator getDwcUsernameField() {
        return dwcUsernameField;
    }

    public Locator getDwcPasswordField() {
        return dwcPasswordField;
    }
}