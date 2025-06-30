package pages.AlertPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

/**
 * Page Object for Alert Themes functionality
 */
public class AlertThemesPage extends BasePage {

    private static final String ROUTE = "alertthemes";

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
        dangerAlert = page.locator("dwc-alert[dwc-id='11']");
        defaultAlert = page.locator("dwc-alert[dwc-id='15']");
        grayAlert = page.locator("dwc-alert[dwc-id='19']");
        infoAlert = page.locator("dwc-alert[dwc-id='23']");
        primaryAlert = page.locator("dwc-alert[dwc-id='27']");
        successAlert = page.locator("dwc-alert[dwc-id='31']");
        warningAlert = page.locator("dwc-alert[dwc-id='35']");
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