package tests.fields.maskeddatefield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;

import pages.fields.maskeddatefield.MaskedDateFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedDateFieldRestoreIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupRestore() {
        navigateToRoute(MaskedDateFieldPage.getRouteRestore());
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @BrowserTest
    public void testResetValue() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        assertThat(maskedDateField.getDateField()).hasValue(today);

        maskedDateField.getResetValueButton().click();
        assertThat(maskedDateField.getDateField()).hasValue(yesterday);

    }
}