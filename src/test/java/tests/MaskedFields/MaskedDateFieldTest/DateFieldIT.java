package tests.MaskedFields.MaskedDateFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;

import pages.MaskedFields.MaskedDateFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DateFieldIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupDateField() {
        page.navigate("https://docs.webforj.com/webforj/maskeddatefield?");
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @BrowserTest
    public void testInitialValue() {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));

        assertThat(maskedDateField.getDateField()).hasValue(today);
    }

    @BrowserTest
    public void testValidDate() {
        maskedDateField.cleanDateField();
        maskedDateField.getDateField().fill("1/1/25");

        maskedDateField.getHelperText().click();

        assertThat(maskedDateField.getDateField()).hasValue("01/01/25");

    }

    @BrowserTest
    public void testPartialInput() {
        maskedDateField.cleanDateField();
        maskedDateField.getDateField().fill("12/1");

        assertThat(maskedDateField.getDateField()).not().hasValue("12/01/25");

    }
} 