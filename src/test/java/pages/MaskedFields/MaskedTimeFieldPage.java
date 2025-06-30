package pages.MaskedFields;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class MaskedTimeFieldPage extends BasePage {

    private final Locator meetingTime;
    private final Locator resetValueButton;
    private final Locator timeOptionDropdown;
    private final Locator spinnerUp;
    private final Locator spinnerDown;
    private final Locator timeSlotIcon;

    public MaskedTimeFieldPage(Page page) {
        super(page);

        meetingTime = page.locator("dwc-timefield[dwc-id='11'] >> input");
        resetValueButton = page.locator("dwc-button[dwc-id='12'] >> button");
        timeOptionDropdown = page.locator("dwc-listbox");
        spinnerUp = page.locator("dwc-icon-button[part='up-button']");
        spinnerDown = page.locator("dwc-icon-button[part='down-button']");
        timeSlotIcon = page.locator("dwc-icon-button[part='picker-button']");

    }

    public Locator getMeetingTime() {
        return meetingTime;
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
