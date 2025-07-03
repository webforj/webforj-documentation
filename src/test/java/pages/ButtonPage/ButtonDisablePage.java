package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;
import com.webforj.samples.config.RouteConfig;

public class ButtonDisablePage extends BasePage {

    private static final String ROUTE = RouteConfig.BUTTON_DISABLE;

    private final Locator disabledButton;
    private final Locator emailField;

    public ButtonDisablePage(Page page) {
        super(page);

        disabledButton = page.locator("dwc-button.hydrated >> button");
        emailField = page.locator("dwc-field[type='email'] >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDisabledButton() {
        return disabledButton;
    }

    public Locator getEmailField() {
        return emailField;
    }
}