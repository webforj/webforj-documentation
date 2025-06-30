package pages.MaskedFields;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class MaskedDateFieldPage extends BasePage {

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

        dateField = page.locator("dwc-datefield[dwc-id='11'] >> input");
        helperText = page.locator("dwc-datefield[dwc-id] >> div[part='helper-text']");
        resetValueButton = page.locator("dwc-button[dwc-id='12']");
        calendar = page.locator("div.flatpickr-calendar");
        spinnerUp = page.locator("dwc-icon-button[part='up-button']");
        spinnerDown = page.locator("dwc-icon-button[part='down-button']");
        dateFieldIcon = page.locator("dwc-datefield[dwc-id='11'] >> dwc-icon-button[part='calendar-button']");
        nextMonthNavigator = page.locator(".flatpickr-next-month");
        previousMonthNavigator = page.locator(".flatpickr-prev-month");
    }

    public Locator getDateField() {
        return dateField;
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
