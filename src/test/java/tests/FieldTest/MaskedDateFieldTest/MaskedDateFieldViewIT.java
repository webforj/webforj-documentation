package tests.FieldTest.MaskedDateFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.MaskedDateField.MaskedDateFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedDateFieldViewIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupDateField() {
        navigateToRoute(MaskedDateFieldPage.getRouteView());
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @BrowserTest
    public void testValidDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        assertThat(maskedDateField.getDateField()).hasValue(today);

        maskedDateField.cleanDateField();
        maskedDateField.getDateField().pressSequentially("1/1/25");

        maskedDateField.getHelperText().click();

        assertThat(maskedDateField.getDateField()).hasValue("01/01/25");

    }

    @BrowserTest
    public void testPartialInput() {
        maskedDateField.cleanDateField();
        maskedDateField.getDateField().pressSequentially("12/1");

        assertThat(maskedDateField.getDateField()).not().hasValue("12/01/25");

    }
}