package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private final Locator header;
    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator passwordReveal;
    private final Locator rememberMe;
    private final Locator errorMessage;
    private final Locator dwcUsernameField;
    private final Locator dwcPasswordField;
    private final Locator logoutButton;
    private final Locator customerID;
    private final Locator cancelButton;

    public LoginPage(Page page) {
        super(page);

        this.header = page.locator("dwc-dialog > header");
        this.username = page.locator("dwc-field#username").locator("input");
        this.password = page.locator("dwc-field#password").locator("input");
        this.signInButton = page.locator("dwc-button[part=\"submit-button\"]");
        this.passwordReveal = page.locator("dwc-icon-button[part=\"eye-off-icon\"]");
        this.rememberMe = page.locator("#checkbox-1");
        this.errorMessage = page.locator("dwc-alert[part=\"error\"]");
        this.dwcUsernameField = page.locator("#username");
        this.dwcPasswordField = page.locator("#password");
        this.logoutButton = page.locator("div[dwc-id=\"10\"] > dwc-button");
        this.customerID = page.locator("dwc-field[dwc-id=\"13\"] >> div > div > input");
        this.cancelButton = page.locator("dwc-button >> button[name=\"Cancel\"]");
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

    public Locator getRememberMe() {
        return rememberMe;
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }

    public Locator getDwcUsernameField() {
        return dwcUsernameField;
    }

    public Locator getDwcPasswordField() {
        return dwcPasswordField;
    }

    public Locator getLogoutButton() {
        return logoutButton;
    }

    public Locator getCustomderID() {
        return customerID;
    }

    public Locator getCancelButton() {
        return cancelButton;
    }
}
