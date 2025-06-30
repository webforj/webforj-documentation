package pages.GoogleChartsPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;
import utils.WaitUtil;

public class ChartRedrawPage extends BasePage {

    private final Locator valueForInstagram;
    private final Locator valueForTwitter;
    private final Locator valueForFacebook;
    private final Locator valueForLinkedIn;
    private final Locator updateButton;
    private final Locator warningMessage;
    private final Locator warningToast;

    public ChartRedrawPage(Page page) {
        super(page);
        this.page = page;
        this.valueForInstagram = page.locator("dwc-field[dwc-id='14'] input");
        this.valueForTwitter = page.locator("dwc-field[dwc-id='15'] input");
        this.valueForFacebook = page.locator("dwc-field[dwc-id='16'] input");
        this.valueForLinkedIn = page.locator("dwc-field[dwc-id='17'] input");
        this.updateButton = page.locator("dwc-button[dwc-id='19']");
        this.warningMessage = page.locator("dwc-alert.bbj-noborder");
        this.warningToast = page.locator("dwc-toast-group[placement='bottom']");
    }

    public void updateChart(int instagram, int twitter, int facebook, int linkedIn) {
        WaitUtil.waitForVisible(valueForInstagram);
        WaitUtil.waitForVisible(valueForTwitter);
        WaitUtil.waitForVisible(valueForFacebook);
        WaitUtil.waitForVisible(valueForLinkedIn);

        valueForInstagram.fill(String.valueOf(instagram));
        valueForTwitter.fill(String.valueOf(twitter));
        valueForFacebook.fill(String.valueOf(facebook));
        valueForLinkedIn.fill(String.valueOf(linkedIn));

        updateButton.click();
    }

    public Locator getValueForInstagram() {
        return valueForInstagram;
    }

    public Locator getValueForTwitter() {
        return valueForTwitter;
    }

    public Locator getValueForFacebook() {
        return valueForFacebook;
    }

    public Locator getValueForLinkedIn() {
        return valueForLinkedIn;
    }

    public Locator getUpdateButton() {
        return updateButton;
    }

    public Locator getWarningMessage() {
        return warningMessage;
    }

    public Locator getWarningToast(){
        return warningToast;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");
    }
}