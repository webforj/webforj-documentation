package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class PasswordFieldPage extends BasePage {

    private static final String ROUTE = RouteConfig.PASSWORD_FIELD;

    private final Locator passwordField;
    private final Locator eyeOffIcon;

    public PasswordFieldPage(Page page) {
        super(page);
        passwordField = page.locator("dwc-field:has-text('Password') >> input");
        eyeOffIcon = page.locator("dwc-icon-button[part='eye-off-icon']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPasswordField() {
        return passwordField;
    }

    public Locator getEyeOffIcon() {
        return eyeOffIcon;
    }
}