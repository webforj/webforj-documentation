package pages.ButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ButtonDemoPage extends BasePage {

    private static final String ROUTE = "buttondemo";

    private final Locator submitButton;
    private final Locator clearButton;
    
    public ButtonDemoPage(Page page) {
        super(page);

        submitButton = page.locator("dwc-button[dwc-id='17'] >> button");
        clearButton = page.locator("dwc-button[dwc-id='18'] >> button");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getClearButton() {
        return clearButton;
    }
} 