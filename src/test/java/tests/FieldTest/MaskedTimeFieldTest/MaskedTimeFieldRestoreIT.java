package tests.FieldTest.MaskedTimeFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.MaskedTimeField.MaskedTimeFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedTimeFieldRestoreIT extends BaseTest {

    private MaskedTimeFieldPage maskedTime;

    @BeforeEach
    public void setupMaskedTimeFieldRestore() {
        navigateToRoute(MaskedTimeFieldPage.getRouteRestore());
        maskedTime = new MaskedTimeFieldPage(page);
    }

    @BrowserTest
    public void testRestoreOnClickButton() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:34 PM");
        maskedTime.getResetValueButton().click();

        assertThat(maskedTime.getMeetingTime()).hasValue("02:00 pm");
    }

    @BrowserTest
    public void testRestoreOnPressEsc() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getMeetingTime().fill("12:34 PM");
        page.keyboard().press("Escape");

        assertThat(maskedTime.getMeetingTime()).hasValue("02:00 pm");
    }

    @BrowserTest
    public void testClearAndRestoreOnClick() {
        maskedTime.cleanField(maskedTime.getMeetingTime());
        maskedTime.getResetValueButton().click();

        assertThat(maskedTime.getMeetingTime()).hasValue("02:00 pm");
    }
}