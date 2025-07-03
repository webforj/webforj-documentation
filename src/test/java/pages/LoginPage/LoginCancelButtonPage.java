package pages.LoginPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class LoginCancelButtonPage extends BasePage {

    private static final String ROUTE = RouteConfig.LOGIN_CANCEL_BUTTON;

    private final Locator cancelButton;
    private final Locator signInButton;

    public LoginCancelButtonPage(Page page) {
        super(page);

        cancelButton = page.locator("dwc-button >> button[name='Cancel']");
        signInButton = page.locator("dwc-button[part='submit-button']");
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