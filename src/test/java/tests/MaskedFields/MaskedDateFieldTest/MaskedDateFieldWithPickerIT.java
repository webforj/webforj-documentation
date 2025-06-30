package tests.MaskedFields.MaskedDateFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.MaskedFields.MaskedDateFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedDateFieldWithPickerIT extends BaseTest {

    private MaskedDateFieldPage maskedDateField;

    @BeforeEach
    public void setupPicker() {
        page.navigate("https://docs.webforj.com/webforj/maskeddatefieldpicker?");
        maskedDateField = new MaskedDateFieldPage(page);
    }

    @BrowserTest
    public void testCalenderAccessibility() {
        Locator weekWrapper = page.locator(".flatpickr-weeks");
        maskedDateField.getDateField().click();

        assertThat(weekWrapper).isVisible();
        assertThat(maskedDateField.getCalendar()).hasAttribute("class", Pattern.compile(".*open.*"));

    }
} 