package pages.RadioButtonPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class RadioButtonGroupBasicPage extends BasePage {

    private static final String ROUTE = RouteConfig.RADIO_BUTTON_GROUP;

    private final Locator stronglyDisagreeRB;
    private final Locator disagreeRB;
    private final Locator neutralRB;
    private final Locator agreeRB;
    private final Locator stronglyAgreeRB;

    public RadioButtonGroupBasicPage(Page page) {
        super(page);

        stronglyDisagreeRB = page.locator("dwc-radio:has-text('Strongly disagree') >> label");
        disagreeRB = page.locator("dwc-radio:has-text('Disagree') >> label");
        neutralRB = page.locator("dwc-radio:has-text('Neutral') >> label");
        agreeRB = page.locator("dwc-radio:has-text('Agree') >> label");
        stronglyAgreeRB = page.locator("dwc-radio:has-text('Strongly agree') >> label");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getStronglyDisagreeRB() {
        return stronglyDisagreeRB;
    }

    public Locator getDisagreeRB() {
        return disagreeRB;
    }

    public Locator getNeutralRB() {
        return neutralRB;
    }

    public Locator getAgreeRB() {
        return agreeRB;
    }

    public Locator getStronglyAgreeRB() {
        return stronglyAgreeRB;
    }
}