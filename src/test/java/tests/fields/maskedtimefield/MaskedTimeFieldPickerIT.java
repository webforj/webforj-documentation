package tests.fields.maskedtimefield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.PlaywrightException;

import pages.fields.maskedtimefield.MaskedTimeFieldPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class MaskedTimeFieldPickerIT extends BaseTest {

    private MaskedTimeFieldPage maskedTime;

    @BeforeEach
    public void setupMaskedTimeFieldPicker() {
        navigateToRoute(MaskedTimeFieldPage.getRoutePicker());
        maskedTime = new MaskedTimeFieldPage(page);
    }

    @BrowserTest
    public void testSelection() {
        maskedTime.getTimeOptionDropdown().locator("text=10:00 am").click();
        assertThat(maskedTime.getMeetingTime()).hasValue("10:00 am");

    }

    @BrowserTest
    public void testPickerClosesOnOutsideClickOrSelection() {
        assertThat(maskedTime.getMeetingTime()).hasValue("09:30 am");

        maskedTime.getMeetingTime().click();
        assertThat(maskedTime.getTimeOptionDropdown()).isHidden();

        maskedTime.getMeetingTime().click();
        WaitUtil.waitForVisible(maskedTime.getMeetingTime());

        maskedTime.getTimeOptionDropdown().locator("text=12:00 am").click();
        assertThat(maskedTime.getMeetingTime()).hasValue("12:00 am");

    }

    @BrowserTest
    public void testRestrictManualInputNotInPickerOptions() {
        try {
            maskedTime.getMeetingTime().fill("12:00 pm");
        } catch (PlaywrightException e) {

        }

        assertThat(maskedTime.getMeetingTime()).hasValue("09:30 am");

    }
}