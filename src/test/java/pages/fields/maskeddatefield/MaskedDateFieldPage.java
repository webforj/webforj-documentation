
package pages.fields.maskeddatefield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class MaskedDateFieldPage extends BasePage {

    private static final String ROUTE_VIEW = "maskeddatefield";
    private static final String ROUTE_PICKER = "maskeddatefieldpicker";
    private static final String ROUTE_RESTORE = "maskeddatefieldrestore";
    private static final String ROUTE_SPINNER = "maskeddatefieldspinner";

    private final Locator dateField;
    private final Locator helperText;
    private final Locator resetValueButton;
    private final Locator calendar;
    private final Locator spinnerUp;
    private final Locator spinnerDown;
    private final Locator dateFieldIcon;
    private final Locator nextMonthNavigator;
    private final Locator previousMonthNavigator;

    public MaskedDateFieldPage(Page page) {
        super(page);

        dateField = page.locator("input[aria-describedby='helper-text']");
        helperText = page.locator("dwc-datefield >> div[part='helper-text']");
        resetValueButton = page.locator("dwc-button >> text=Reset Value");
        calendar = page.locator("div.flatpickr-calendar");
        spinnerUp = page.locator("dwc-icon-button[part='up-button']");
        spinnerDown = page.locator("dwc-icon-button[part='down-button']");
        dateFieldIcon = page.locator("dwc-icon-button[part='calendar-button']");
        nextMonthNavigator = page.locator(".flatpickr-next-month");
        previousMonthNavigator = page.locator(".flatpickr-prev-month");
    }

    public Locator getDateField() {
        return dateField;
    }

    public static String getRouteView() {
        return ROUTE_VIEW;
    }

    public static String getRoutePicker() {
        return ROUTE_PICKER;
    }

    public static String getRouteSpinner() {
        return ROUTE_SPINNER;
    }

    public static String getRouteRestore() {
        return ROUTE_RESTORE;
    }
    public Locator getHelperText() {
        return helperText;
    }

    public Locator getResetValueButton() {
        return resetValueButton;
    }

    public Locator getCalendar() {
        return calendar;
    }

    public Locator getSpinnerUp() {
        return spinnerUp;
    }

    public Locator getSpinnerDown() {
        return spinnerDown;
    }

    public Locator getDateFieldIcon() {
        return dateFieldIcon;
    }

    public Locator getNextMonthNavigator() {
        return nextMonthNavigator;
    }

    public Locator getPreviousMonthNavigator() {
        return previousMonthNavigator;
    }

    public void cleanDateField() {
        dateField.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");

    }
}
