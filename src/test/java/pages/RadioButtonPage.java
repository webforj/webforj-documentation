package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RadioButtonPage extends BasePage {

    // RadioButtonGroup
    private final Locator stronglyDisagreeInput;
    private final Locator agreeInput;

    // RadioButtonText
    private final Locator rightAlignedInput;
    private final Locator leftAlignedInput;

    // RadioButtonActivation
    private final Locator autoActivatedInput;

    // RadioButtonSwitch
    private final Locator switchRadio;
    private final Locator switchInput;

    public RadioButtonPage(Page page) {
        super(page);

        // RadioButtonGroup
        stronglyDisagreeInput = page.locator("dwc-radio[dwc-id='12'] >> input");
        agreeInput = page.locator("dwc-radio[dwc-id='16'] >> input");

        // RadioButtonText
        rightAlignedInput = page.locator("dwc-radio[dwc-id='11'] >> input");
        leftAlignedInput = page.locator("dwc-radio[dwc-id='12'] >> input");

        // RadioButtonActivation
        autoActivatedInput = page.locator("dwc-radio[dwc-id='11'] >> input");

        // RadioButtonSwitch
        switchRadio = page.locator("dwc-radio[dwc-id='12']");
        switchInput = switchRadio.locator("input");
    }

    // RadioButtonGroup getters
    public Locator getStronglyDisagreeInput() {
        return stronglyDisagreeInput;
    }

    public Locator getAgreeInput() {
        return agreeInput;
    }

    // RadioButtonText getters
    public Locator getRightAlignedInput() {
        return rightAlignedInput;
    }

    public Locator getLeftAlignedInput() {
        return leftAlignedInput;
    }

    // RadioButtonActivation getter
    public Locator getAutoActivatedInput() {
        return autoActivatedInput;
    }

    // RadioButtonSwitch getters
    public Locator getSwitchRadio() {
        return switchRadio;
    }

    public Locator getSwitchInput() {
        return switchInput;
    }
}
