package tests.FieldTest.MaskedDateFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.MaskedDateField.MaskedDateFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedDateFieldSpinnerIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupSpinner() {
        navigateToRoute(MaskedDateFieldPage.getRouteSpinner());
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @BrowserTest
    public void testManualInput() {
        maskedDateField.getDateField().click();
        String originalValue = maskedDateField.getDateField().inputValue();

        maskedDateField.getDateField().pressSequentially("01012030");

        assertThat(maskedDateField.getDateField()).hasValue(originalValue);
        assertThat(maskedDateField.getDateField()).hasAttribute("readonly", "");
    }

    @BrowserTest
    public void testSpinner() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String tomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        maskedDateField.getSpinnerUp().click();
        assertThat(maskedDateField.getDateField()).hasValue(tomorrow);

        maskedDateField.getSpinnerDown().click();

        assertThat(maskedDateField.getDateField()).hasValue(today);
    }

    @BrowserTest
    public void testFutureDateLimitation() {
        maskedDateField.getDateFieldIcon().click();

        for (int i = 0; i < 6; i++) {
            maskedDateField.getNextMonthNavigator().click();
        }

        assertThat(maskedDateField.getNextMonthNavigator()).hasAttribute("class",
                Pattern.compile(".*flatpickr-disabled.*"));
    }
}