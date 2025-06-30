package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ButtonDisablePage extends BasePage {

    private static final String ROUTE = "buttondisable";

    private final Locator disabledButton;
    private final Locator emailField;
    
    public ButtonDisablePage(Page page) {
        super(page);

        disabledButton = page.locator("dwc-button[dwc-id='12'] >> button");
        emailField = page.locator("dwc-field[dwc-id='11'] >> input");
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