package pages.LoginPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class LoginInternationalizationPage extends BasePage {

    private static final String ROUTE = RouteConfig.LOGIN_INTERNATIONALIZATION;

    private final Locator header;
    private final Locator errorMessage;

    public LoginInternationalizationPage(Page page) {
        super(page);

        header = page.locator("dwc-dialog > header");
        errorMessage = page.locator("dwc-alert[part='error']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHeader() {
        return header;
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }
}