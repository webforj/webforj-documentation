package tests.FieldTest.MaskedTextFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.FieldPages.MaskedTextField.MaskedTextFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedTextFieldWithRestoreValueTestsIT extends BaseTest {

    private MaskedTextFieldPage maskedTextField;
    final String INITIAL_ZIP_VALUE = "85001 PHX";

    @BeforeEach
    void setupMaskedTextFieldWithRestoreValueTests() {
        navigateToRoute(MaskedTextFieldPage.getRouteRestore());
        maskedTextField = new MaskedTextFieldPage(page);
    }

    @BrowserTest
    void testRestoreWorksAfterManualChange() {
        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().type("94131 SFO");
        assertThat(maskedTextField.getPostalCode()).hasValue("94131 SFO");
    }

    @BrowserTest
    void testESCRestoresToSetValue() {

        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().type("94131 SFO");
        assertThat(maskedTextField.getPostalCode()).hasValue("94131 SFO");

        page.keyboard().press("Escape");
        assertThat(maskedTextField.getPostalCode()).hasValue(INITIAL_ZIP_VALUE);
    }

    @BrowserTest
    void testRestoreWithEmptyOrNullRestoreValue() {

        maskedTextField.cleanField(maskedTextField.getPostalCode());
        maskedTextField.getRestoreButton().click();
        assertThat(maskedTextField.getPostalCode()).hasValue(INITIAL_ZIP_VALUE);

        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().press("Escape");
        assertThat(maskedTextField.getPostalCode()).hasValue(INITIAL_ZIP_VALUE);
    }

    @BrowserTest
    void testRestoreValueWithSpecialCharsOrInvalidFormat() {
        maskedTextField.cleanField(maskedTextField.getPostalCode());
        page.keyboard().type("!@#$% !#@");
        Locator parentElement = page.locator("dwc-textfield:has-text('Postal Code')");
        assertThat(parentElement).hasAttribute("invalid", "");
    }
}