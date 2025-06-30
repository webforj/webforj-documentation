package pages.AlertPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

/**
 * Page Object for Form Confirmation Alert functionality
 */
public class FormConfirmationAlertPage extends BasePage {

    private static final String ROUTE = "alert";

    // FormConfirmationAlert locators
    private final Locator formAlert;
    private final Locator formAlertText;
    private final Locator formAlertButton;

    public FormConfirmationAlertPage(Page page) {
        super(page);
        
        // Initialize FormConfirmationAlert locators
        formAlert = page.locator("dwc-alert[dwc-id='11']");
        formAlertText = page.locator("p[dwc-id='12']");
        formAlertButton = page.locator("dwc-button[dwc-id='13']");
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