package pages.MaskedFields;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class MaskedNumberFieldPage extends BasePage {

    private final Locator amountField;
    private final Locator tipPercentageField;
    private final Locator calculateTipButton;
    private final Locator toast;
    private final Locator toastTheme;
    private final Locator negatableRadio;
    private final Locator resetValueButton;
    private final Locator spinnerUp;
    private final Locator spinnerDown;
    private final Locator warningPopover;

    public MaskedNumberFieldPage(Page page) {
        super(page);

        amountField = page.locator("dwc-numberfield[dwc-id='11'] >> input[type=\"text\"]");
        tipPercentageField = page.locator("dwc-numberfield[dwc-id='13'] >> input[part='input input-masked']");
        calculateTipButton = page.locator("dwc-button[dwc-id='15']");
        toast = page.locator("dwc-toast-group[placement='bottom']");
        toastTheme = page.locator("dwc-toast-group[placement='bottom'] > dwc-toast");
        negatableRadio = page.locator("dwc-radio[dwc-id='12'] >> input");
        resetValueButton = page.locator("dwc-button[dwc-id='12'] >> button");
        spinnerUp = page.locator("dwc-icon-button[part=\"up-button\"]");
        spinnerDown = page.locator("dwc-icon-button[part=\"down-button\"]");
        warningPopover = page.locator(".dwc-positioner--popover");
    }

    public Locator getAmountField() {
        return amountField;
    }

    public Locator getTipPercentageField() {
        return tipPercentageField;
    }

    public Locator getCalculateTipButton() {
        return calculateTipButton;
    }

    public Locator getToast() {
        return toast;
    }

    public Locator getNegatableToggle() {
        return negatableRadio;
    }

    public Locator getToastTheme() {
        return toastTheme;
    }

    public Locator getResetValueButton() {
        return resetValueButton;
    }

    public Locator getSpinnerUp() {
        return spinnerUp;
    }

    public Locator getSpinnerDown() {
        return spinnerDown;
    }

    public Locator getWarningPopover() {
        return warningPopover;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");

    }
}
