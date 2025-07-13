package tests.FieldTest.MaskedDateFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pages.FieldPages.MaskedDateField.MaskedDateFieldPage;
import tests.BaseTest;

public class MaskedDateFieldViewIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupDateField() {
        navigateToRoute(MaskedDateFieldPage.getRouteView());
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @Test
    public void testValidDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        assertThat(maskedDateField.getDateField()).hasValue(today);

        maskedDateField.cleanDateField();
        maskedDateField.getDateField().fill("1/1/25");

        maskedDateField.getHelperText().click();

        assertThat(maskedDateField.getDateField()).hasValue("01/01/25");

    }

    @Test
    public void testPartialInput() {
        maskedDateField.cleanDateField();
        maskedDateField.getDateField().fill("12/1");

        assertThat(maskedDateField.getDateField()).not().hasValue("12/01/25");

    }
}