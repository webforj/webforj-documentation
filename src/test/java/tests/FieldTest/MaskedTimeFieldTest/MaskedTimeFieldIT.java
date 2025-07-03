package tests.FieldTest.MaskedTimeFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.MaskedTimeField.MaskedTimeFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedTimeFieldIT extends BaseTest {

    private MaskedTimeFieldPage maskedTime;

    @BeforeEach
    public void setupMaskedTimeField() {
        navigateToRoute(MaskedTimeFieldPage.getRouteView());
        maskedTime = new MaskedTimeFieldPage(page);
    }

    @BrowserTest
    public void testAcceptsValidTimeFormat() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:34 PM");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:34 PM");
    }

    @BrowserTest
    public void testHourMaskSupportsValidAndInvalid() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("15:30 PM");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("3:30 pm");
    }

    @BrowserTest
    public void testMinuteZeroFill() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:3 PM");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:03 pm");
    }

    @BrowserTest
    public void testSeparatorEnforcement() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("1230pm");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:30 pm");
    }

    @BrowserTest
    public void testInvalidMinutesCorrection() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:61 pm");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("1:01 pm");
    }

    @BrowserTest
    public void testInvalidCharactersBlocked() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("!:00 AM");
        page.keyboard().press("Enter");

        assertThat(maskedTime.getMeetingTime()).hasValue("12:00 am");
    }
}