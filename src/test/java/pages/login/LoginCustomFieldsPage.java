package pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class LoginCustomFieldsPage extends BasePage {

    private static final String ROUTE = "logincustomfields";

    private final Locator header;
    private final Locator username;
    private final Locator password;
    private final Locator signInButton;
    private final Locator errorMessage;
    private final Locator logoutButton;
    private final Locator customerID;

    public LoginCustomFieldsPage(Page page) {
        super(page);

        header = page.locator("dwc-dialog > header");
        username = page.locator("dwc-field#username >> input");
        password = page.locator("dwc-field#password >> input");
        signInButton = page.locator("dwc-button[part='submit-button']");
        errorMessage = page.locator("dwc-alert[part='error']");
        logoutButton = page.locator("div:has-text('Logout') > dwc-button");
        customerID = page.locator("dwc-field:has-text('Customer ID') >> div > div > input");
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

    public Locator getErrorMessage() {
        return errorMessage;
    }

    public Locator getLogoutButton() {
        return logoutButton;
    }

    public Locator getCustomderID() {
        return customerID;
    }
}