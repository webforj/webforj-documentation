package tests.FieldTest.MaskedTextFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

import pages.FieldPages.MaskedTextField.MaskedTextFieldPage;
import tests.BaseTest;

public class MaskedTextFieldWithRestoreValueTestsIT extends BaseTest {

    private MaskedTextFieldPage maskedTextField;
    final String INITIAL_ZIP_VALUE = "85001 PHX";

    @BeforeEach
    void setupMaskedTextFieldWithRestoreValueTests() {
        navigateToRoute(MaskedTextFieldPage.getRouteRestore());
        maskedTextField = new MaskedTextFieldPage(page);
    }

    @Test
    void testRestoreWorksAfterManualChange() {
        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().type("94131 SFO");
        assertThat(maskedTextField.getPostalCode()).hasValue("94131 SFO");
    }

    @Test
    void testESCRestoresToSetValue() {

        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().type("94131 SFO");
        assertThat(maskedTextField.getPostalCode()).hasValue("94131 SFO");

        page.keyboard().press("Escape");
        assertThat(maskedTextField.getPostalCode()).hasValue(INITIAL_ZIP_VALUE);
    }

    @Test
    void testRestoreWithEmptyOrNullRestoreValue() {

        maskedTextField.cleanField(maskedTextField.getPostalCode());
        maskedTextField.getRestoreButton().click();
        assertThat(maskedTextField.getPostalCode()).hasValue(INITIAL_ZIP_VALUE);

        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().press("Escape");
        assertThat(maskedTextField.getPostalCode()).hasValue(INITIAL_ZIP_VALUE);
    }

    @Test
    void testRestoreValueWithSpecialCharsOrInvalidFormat() {
        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().type("!@#$% !#@");
        Locator parentElement = page.locator("dwc-textfield:has-text('Postal Code')");
        assertThat(parentElement).hasAttribute("invalid", "");
    }
}