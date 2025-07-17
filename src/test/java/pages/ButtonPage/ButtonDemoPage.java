package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;
import config.RouteConfig;

public class ButtonDemoPage extends BasePage {

    private static final String ROUTE = RouteConfig.BUTTON_DEMO;

    private final Locator submitButton;
    private final Locator clearButton;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator email;

    public ButtonDemoPage(Page page) {
        super(page);

        firstName = page.locator("dwc-field:has-text('First Name') >> input");
        lastName = page.locator("dwc-field:has-text('Last Name') >> input");
        email = page.locator("dwc-field[type='email'] >> input");

        submitButton = page.locator("dwc-button:has-text('Submit')");
        clearButton = page.locator("dwc-button:has-text('Clear')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFirstName() {
        return firstName;
    }

    public Locator getLastName() {
        return lastName;
    }

    public Locator getEmail() {
        return email;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getClearButton() {
        return clearButton;
    }
}