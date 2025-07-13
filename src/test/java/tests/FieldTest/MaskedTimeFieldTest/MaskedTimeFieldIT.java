package tests.FieldTest.MaskedTimeFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pages.FieldPages.MaskedTimeField.MaskedTimeFieldPage;
import tests.BaseTest;

public class MaskedTimeFieldIT extends BaseTest {

    private MaskedTimeFieldPage maskedTime;

    @BeforeEach
    public void setupMaskedTimeField() {
        navigateToRoute(MaskedTimeFieldPage.getRouteView());
        maskedTime = new MaskedTimeFieldPage(page);
    }

    @Test
    public void testAcceptsValidTimeFormat() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:34 PM");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:34 PM");
    }

    @Test
    public void testHourMaskSupportsValidAndInvalid() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("15:30 PM");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("3:30 pm");
    }

    @Test
    public void testMinuteZeroFill() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:3 PM");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:03 pm");
    }

    @Test
    public void testSeparatorEnforcement() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("1230pm");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:30 pm");
    }

    @Test
    public void testInvalidMinutesCorrection() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:61 pm");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("1:01 pm");
    }

    @Test
    public void testInvalidCharactersBlocked() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("!:00 AM");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:00 am");
    }
}