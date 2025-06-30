package pages.MaskedFields;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class MaskedTextFieldPage extends BasePage {

    private final Locator couponCode;
    private final Locator recordCode;
    private final Locator postalCode;
    private final Locator restoreButton;
    private final Locator projectCode;
    private final Locator spinnerUp;
    private final Locator spinnerDown;

    public MaskedTextFieldPage(Page page) {
        super(page);

        couponCode = page.locator("dwc-textfield[dwc-id='11'] >> input[part='input input-masked']");
        recordCode = page.locator("dwc-textfield[dwc-id='12'] >> input[part='input input-masked']");
        postalCode = page.locator("dwc-textfield[dwc-id='11'] >> input[part='input input-masked']");
        restoreButton = page.locator("dwc-button[dwc-id='12']");
        projectCode = page.locator("dwc-textfield[dwc-id='11'] >> input[part='input input-masked']");
        spinnerUp = page.locator("dwc-icon-button[part='up-button'] >> button");
        spinnerDown = page.locator("dwc-icon-button[part='down-button'] >> button");
    }

    public Locator getCouponCode() {
        return couponCode;
    }

    public Locator getRecordCode() {
        return recordCode;
    }

    public Locator getPostalCode() {
        return postalCode;
    }

    public Locator getRestoreButton() {
        return restoreButton;
    }

    public Locator getProjectCode() {
        return projectCode;
    }

    public Locator getSpinnerUp() {
        return spinnerUp;
    }

    public Locator getSpinnerDown() {
        return spinnerDown;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");    
    }

    public void copyToClipboard(String textToCopy) {
        page.evaluate("text => navigator.clipboard.writeText(text)", textToCopy);
    }

    public void pasteFromClipboard(Locator field) {
        field.focus();
        page.keyboard().press("Control+V"); // Control+V for Windows/Linux, Meta+V (Cmd+V) for macOS
    }

}
