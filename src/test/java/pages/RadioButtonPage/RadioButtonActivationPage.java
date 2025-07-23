package pages.RadioButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class RadioButtonActivationPage extends BasePage {

    private static final String ROUTE = RouteConfig.RADIO_BUTTON_ACTIVATION;

    private final Locator autoActivatedInput;

    public RadioButtonActivationPage(Page page) {
        super(page);

        autoActivatedInput = page.locator("dwc-radio:has-text('Auto Activated')").nth(0).locator("input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getAutoActivatedInput() {
        return autoActivatedInput;
    }
}