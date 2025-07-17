package pages.FieldPages.MaskedNumberField;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import config.RouteConfig;

import pages.BasePage;

public class MaskedNumberFieldPage extends BasePage {

    private static final String ROUTE_VIEW = RouteConfig.MASKED_NUMBER_FIELD_VIEW;
    private static final String ROUTE_NEGATABLE = RouteConfig.MASKED_NUMBER_FIELD_NEGATABLE;
    private static final String ROUTE_RESTORE = RouteConfig.MASKED_NUMBER_FIELD_RESTORE;
    private static final String ROUTE_SPINNER = RouteConfig.MASKED_NUMBER_FIELD_SPINNER;

    private final Locator amountField;
    private final Locator tipPercentageField;
    private final Locator creditField;
    private final Locator projectBudgetField;
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

        amountField = page.locator("dwc-numberfield:has-text('Bill Amount') >> input").first();
        tipPercentageField = page.locator("dwc-numberfield:has-text('Tip Percentage (%)') >> input").first();
        creditField = page.locator("dwc-numberfield:has-text('Credits') >> input").first();
        projectBudgetField = page.locator("dwc-numberfield:has-text('Project Budget') >> input").first();
        calculateTipButton = page.locator("dwc-button:has-text('Calculate Tip')");
        toast = page.locator("dwc-toast-group[placement='bottom']");
        toastTheme = page.locator("dwc-toast-group[placement='bottom'] > dwc-toast");
        negatableRadio = page.locator("dwc-radio >> input[type='radio']");
        resetValueButton = page.locator("dwc-button:has-text('Reset Value')");
        spinnerUp = page.locator("dwc-icon-button[part='up-button']");
        spinnerDown = page.locator("dwc-icon-button[part='down-button']");
        warningPopover = page.locator(".dwc-positioner--popover");
    }

    public Locator getAmountField() {
        return amountField;
    }

    public static String getRouteView() {
        return ROUTE_VIEW;
    }

    public static String getRouteNegatable() {
        return ROUTE_NEGATABLE;
    }

    public static String getRouteRestore() {
        return ROUTE_RESTORE;
    }

    public static String getRouteSpinner() {
        return ROUTE_SPINNER;
    }

    public Locator getTipPercentageField() {
        return tipPercentageField;
    }

    public Locator getCreditField() {
        return creditField;
    }

    public Locator getProjectBudgetField() {
        return projectBudgetField;
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
