package pages.AlertPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

/**
 * Page Object for Alert Themes functionality
 */
public class AlertThemesPage extends BasePage {

    private static final String ROUTE = RouteConfig.ALERT_THEMES;

    // AlertThemes locators
    private final Locator dangerAlert;
    private final Locator defaultAlert;
    private final Locator grayAlert;
    private final Locator infoAlert;
    private final Locator primaryAlert;
    private final Locator successAlert;
    private final Locator warningAlert;

    public AlertThemesPage(Page page) {
        super(page);

        // Initialize AlertThemes locators
        dangerAlert = page.locator("dwc-alert[theme='danger']");
        defaultAlert = page.locator("dwc-alert[theme='default']");
        grayAlert = page.locator("dwc-alert[theme='gray']");
        infoAlert = page.locator("dwc-alert[theme='info']");
        primaryAlert = page.locator("dwc-alert[theme='primary']");
        successAlert = page.locator("dwc-alert[theme='success']");
        warningAlert = page.locator("dwc-alert[theme='warning']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // AlertThemes getters
    public Locator getDangerAlert() {
        return dangerAlert;
    }

    public Locator getDefaultAlert() {
        return defaultAlert;
    }

    public Locator getGrayAlert() {
        return grayAlert;
    }

    public Locator getInfoAlert() {
        return infoAlert;
    }

    public Locator getPrimaryAlert() {
        return primaryAlert;
    }

    public Locator getSuccessAlert() {
        return successAlert;
    }

    public Locator getWarningAlert() {
        return warningAlert;
    }
}