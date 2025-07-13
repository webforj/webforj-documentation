package tests.FieldTest.MaskedDateFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pages.FieldPages.MaskedDateField.MaskedDateFieldPage;
import tests.BaseTest;

public class MaskedDateFieldWithRestoreIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupRestore() {
        navigateToRoute(MaskedDateFieldPage.getRouteRestore());
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @Test
    public void testResetValue() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        assertThat(maskedDateField.getDateField()).hasValue(today);

        maskedDateField.getResetValueButton().click();
        assertThat(maskedDateField.getDateField()).hasValue(yesterday);

    }
}