package tests.FieldTest.MaskedTimeFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pages.FieldPages.MaskedTimeField.MaskedTimeFieldPage;
import tests.BaseTest;
import utils.WaitUtil;

public class MaskedTimeFieldSpinnerIT extends BaseTest {

    private MaskedTimeFieldPage maskedTime;

    @BeforeEach
    public void setupMaskedTimeFieldSpinner() {
        navigateToRoute(MaskedTimeFieldPage.getRouteSpinner());
        maskedTime = new MaskedTimeFieldPage(page);
    }

    @Test
    public void testSpinnerIncrementDecrement() {
        // click ESC the field to ensure focus is lost
        WaitUtil.waitForVisible(maskedTime.getTimeOptionDropdown());
        page.keyboard().press("Escape");

        assertThat(maskedTime.getMeetingTime()).hasValue("09:00 am");

        maskedTime.getSpinnerUp().click();
        assertThat(maskedTime.getMeetingTime()).hasValue("09:01 am");

        maskedTime.getSpinnerDown().click();
        assertThat(maskedTime.getMeetingTime()).hasValue("09:00 am");

    }

    @Test
    public void testSpinnerRespectsMinMaxBoundaries() {
        // click ESC the field to ensure focus is lost
        WaitUtil.waitForVisible(maskedTime.getTimeOptionDropdown());
        page.keyboard().press("Escape");

        assertThat(maskedTime.getMeetingTime()).hasValue("09:00 am");

        maskedTime.getSpinnerDown().click();
        assertThat(maskedTime.getMeetingTime()).hasValue("09:00 am");

        maskedTime.getTimeSlotIcon().click();
        maskedTime.getTimeOptionDropdown().locator("text=05:00 pm").click();
        assertThat(maskedTime.getMeetingTime()).hasValue("05:00 pm");

        maskedTime.getSpinnerUp().click();
        assertThat(maskedTime.getMeetingTime()).hasValue("05:00 pm");

    }

    @Disabled("Bug report #988")
    @Test
    public void testDisallowManualInputWhenNotAllowed() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:00 pm");
        page.keyboard().press("Enter");

        // default value is 09:00 am
        assertThat(maskedTime.getMeetingTime()).hasValue("09:00 am");
    }
}