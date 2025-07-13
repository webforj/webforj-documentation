package tests.FieldTest.MaskedTimeFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.PlaywrightException;

import pages.FieldPages.MaskedTimeField.MaskedTimeFieldPage;
import tests.BaseTest;
import utils.WaitUtil;

public class MaskedTimeFieldPickerIT extends BaseTest {

    private MaskedTimeFieldPage maskedTime;

    @BeforeEach
    public void setupMaskedTimeFieldPicker() {
        navigateToRoute(MaskedTimeFieldPage.getRoutePicker());
        maskedTime = new MaskedTimeFieldPage(page);
    }

    @Test
    public void testSelection() {
        maskedTime.getTimeOptionDropdown().locator("text=10:00 am").click();
        assertThat(maskedTime.getMeetingTime()).hasValue("10:00 am");

    }

    @Test
    public void testPickerClosesOnOutsideClickOrSelection() {
        assertThat(maskedTime.getMeetingTime()).hasValue("09:30 am");

        maskedTime.getMeetingTime().click();
        assertThat(maskedTime.getTimeOptionDropdown()).isHidden();

        maskedTime.getMeetingTime().click();
        WaitUtil.waitForVisible(maskedTime.getMeetingTime());

        maskedTime.getTimeOptionDropdown().locator("text=12:00 am").click();
        assertThat(maskedTime.getMeetingTime()).hasValue("12:00 am");

    }

    @Test
    public void testRestrictManualInputNotInPickerOptions() {
        try {
            maskedTime.getMeetingTime().fill("12:00 pm");
        } catch (PlaywrightException e) {

        }

        assertThat(maskedTime.getMeetingTime()).hasValue("09:30 am");

    }
}