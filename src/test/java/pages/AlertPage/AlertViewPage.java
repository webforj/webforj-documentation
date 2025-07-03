package pages.AlertPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;
import com.webforj.samples.config.RouteConfig;

/**
 * Page Object for Form Confirmation Alert functionality
 */
public class AlertViewPage extends BasePage {

    private static final String ROUTE = RouteConfig.ALERT;

    // FormConfirmationAlert locators
    private final Locator formAlert;
    private final Locator formAlertText;
    private final Locator formAlertButton;

    public AlertViewPage(Page page) {
        super(page);

        formAlert = page.locator("dwc-alert[theme='primary']");
        formAlertText = page.locator("p:has-text('The requested information is ready to be viewed.')");
        formAlertButton = page.locator("dwc-button:has-text('View')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // FormConfirmationAlert getters
    public Locator getFormAlert() {
        return formAlert;
    }

    public Locator getFormAlertText() {
        return formAlertText;
    }

    public Locator getFormAlertButton() {
        return formAlertButton;
    }
}