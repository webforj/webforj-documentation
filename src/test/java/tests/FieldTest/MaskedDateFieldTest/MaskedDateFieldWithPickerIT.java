package tests.FieldTest.MaskedDateFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

import pages.FieldPages.MaskedDateField.MaskedDateFieldPage;
import tests.BaseTest;

public class MaskedDateFieldWithPickerIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupPicker() {
        navigateToRoute(MaskedDateFieldPage.getRoutePicker());
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @Test
    public void testCalenderAccessibility() {
        Locator weekWrapper = page.locator(".flatpickr-weeks");
        maskedDateField.getDateField().click();

        assertThat(weekWrapper).isVisible();
        assertThat(maskedDateField.getCalendar()).hasAttribute("class", Pattern.compile(".*open.*"));

    }
}