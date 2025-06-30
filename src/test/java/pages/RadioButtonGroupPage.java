package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RadioButtonGroupPage extends BasePage {

    // RadioButtonGroup elements
    private final Locator stronglyDisagreeRB;
    private final Locator disagreeRB;
    private final Locator neutralRB;
    private final Locator agreeRB;
    private final Locator stronglyAgreeRB;

    public RadioButtonGroupPage(Page page) {
        super(page);

        // RadioButtonGroup elements
        stronglyDisagreeRB = page.locator("dwc-radio[dwc-id='12'] >> label");
        disagreeRB = page.locator("dwc-radio[dwc-id='14'] >> label");
        neutralRB = page.locator("dwc-radio[dwc-id='15'] >> label");
        agreeRB = page.locator("dwc-radio[dwc-id='16'] >> label");
        stronglyAgreeRB = page.locator("dwc-radio[dwc-id='17'] >> label");
    }

    // RadioButtonGroup getters
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