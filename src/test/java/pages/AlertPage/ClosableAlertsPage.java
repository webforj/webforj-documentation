package pages.AlertPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

/**
 * Page Object for Closable Alerts functionality
 */
public class ClosableAlertsPage extends BasePage {

    private static final String ROUTE = "closablealert";

    // ClosableAlerts locators
    private final Locator closableAlert;
    private final Locator closableAlertText;
    private final Locator closableAlertButton;
    private final Locator showAlertButton;

    public ClosableAlertsPage(Page page) {
        super(page);
        
        // Initialize ClosableAlerts locators
        closableAlert = page.locator("dwc-alert[dwc-id='11']");
        closableAlertText = page.locator("text='Heads up! This alert can be dismissed.'");
        closableAlertButton = page.locator("dwc-icon-button.hydrated");
        showAlertButton = page.locator("dwc-button[dwc-id='12']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // ClosableAlerts getters
    public Locator getClosableAlert() {
        return closableAlert;
    }

    public Locator getClosableAlertText() {
        return closableAlertText;
    }

    public Locator getClosableAlertButton() {
        return closableAlertButton;
    }

    public Locator getShowAlertButton() {
        return showAlertButton;
    }
} 