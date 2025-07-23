package pages.LoginPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class LoginSubmissionPage extends BasePage {

    private static final String ROUTE = RouteConfig.LOGIN_SUBMISSION;

    private final Locator header;
    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator rememberMe;
    private final Locator errorMessage;
    private final Locator logoutButton;

    public LoginSubmissionPage(Page page) {
        super(page);

        header = page.locator("dwc-dialog > header");
        username = page.locator("dwc-field#username >> input");
        password = page.locator("dwc-field#password >> input");
        signInButton = page.locator("dwc-button[part='submit-button']");
        rememberMe = page.locator("#checkbox-1");
        errorMessage = page.locator("dwc-alert[part='error']");
        logoutButton = page.locator("div:has-text('Logout') > dwc-button");
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

    public Locator getRememberMe() {
        return rememberMe;
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }

    public Locator getLogoutButton() {
        return logoutButton;
    }
}