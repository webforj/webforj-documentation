package pages.LoginPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class LoginBasicPage extends BasePage {

    private static final String ROUTE = RouteConfig.LOGIN_BASIC;

    private final Locator header;
    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator passwordReveal;
    private final Locator rememberMe;
    private final Locator dwcUsernameField;
    private final Locator dwcPasswordField;

    public LoginBasicPage(Page page) {
        super(page);

        header = page.locator("dwc-dialog > header");
        username = page.locator("dwc-field#username >> input");
        password = page.locator("dwc-field#password >> input");
        signInButton = page.locator("dwc-button[part='submit-button']");
        passwordReveal = page.locator("dwc-icon-button[part='eye-off-icon']");
        rememberMe = page.locator("#checkbox-1");
        dwcUsernameField = page.locator("#username");
        dwcPasswordField = page.locator("#password");
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

    public Locator getRememberMe() {
        return rememberMe;
    }

    public Locator getDwcUsernameField() {
        return dwcUsernameField;
    }

    public Locator getDwcPasswordField() {
        return dwcPasswordField;
    }
}