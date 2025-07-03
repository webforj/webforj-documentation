package pages.FieldPages.MaskedTimeField;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class MaskedTimeFieldPage extends BasePage {

    private static final String ROUTE_VIEW = RouteConfig.MASKED_TIME_FIELD_VIEW;
    private static final String ROUTE_RESTORE = RouteConfig.MASKED_TIME_FIELD_RESTORE;
    private static final String ROUTE_SPINNER = RouteConfig.MASKED_TIME_FIELD_SPINNER;
    private static final String ROUTE_PICKER = RouteConfig.MASKED_TIME_FIELD_PICKER;

    private final Locator meetingTime;
    private final Locator resetValueButton;
    private final Locator timeOptionDropdown;
    private final Locator spinnerUp;
    private final Locator spinnerDown;
    private final Locator timeSlotIcon;

    public MaskedTimeFieldPage(Page page) {
        super(page);

        meetingTime = page.locator("dwc-timefield >> input[part='input']");
        resetValueButton = page.locator("dwc-button:has-text('Reset Value')");
        timeOptionDropdown = page.locator("dwc-listbox");
        spinnerUp = page.locator("dwc-icon-button[part='up-button']");
        spinnerDown = page.locator("dwc-icon-button[part='down-button']");
        timeSlotIcon = page.locator("dwc-icon-button[part='picker-button']");

    }

    public Locator getMeetingTime() {
        return meetingTime;
    }

    public static String getRouteView() {
        return ROUTE_VIEW;
    }

    public static String getRouteRestore() {
        return ROUTE_RESTORE;
    }

    public static String getRouteSpinner() {
        return ROUTE_SPINNER;
    }

    public static String getRoutePicker() {
        return ROUTE_PICKER;
    }

    public Locator getResetValueButton() {
        return resetValueButton;
    }

    public Locator getTimeOptionDropdown() {
        return timeOptionDropdown;
    }

    public Locator getSpinnerUp() {
        return spinnerUp;
    }

    public Locator getSpinnerDown() {
        return spinnerDown;
    }

    public Locator getTimeSlotIcon() {
        return timeSlotIcon;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");

    }

}
