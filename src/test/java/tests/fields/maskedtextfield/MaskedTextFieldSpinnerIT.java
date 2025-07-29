package tests.fields.maskedtextfield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Keyboard;

import pages.fields.maskedtextfield.MaskedTextFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedTextFieldSpinnerIT extends BaseTest {

    private MaskedTextFieldPage maskedTextField;

    @BeforeEach
    void setupMaskedTextFieldSpinnerTests() {
        navigateToRoute(MaskedTextFieldPage.getRouteSpinner());
        maskedTextField = new MaskedTextFieldPage(page);
    }

    @BrowserTest
    void testInitialValueAndSpinnerUpandDownCycle() {
        assertThat(maskedTextField.getProjectCode()).hasValue("PRJ-001");
        maskedTextField.getSpinnerUp().click();
        assertThat(maskedTextField.getProjectCode()).hasValue("PRJ-002");

        maskedTextField.getSpinnerDown().dblclick();
        assertThat(maskedTextField.getProjectCode()).hasValue("PRJ-004");
    }

    @BrowserTest
    void testMaskPatternEnforcementAAA000() {
        // AAA - Any alphabetic character; lowercase letters are converted to uppercase.
        // 000 - Always digit.

        maskedTextField.cleanField(maskedTextField.getProjectCode());
        page.keyboard().type("prj-001", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getProjectCode()).hasValue("PRJ-001");

        maskedTextField.cleanField(maskedTextField.getProjectCode());
        page.keyboard().type("xyz-!@#", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getProjectCode()).hasValue("XYZ-   ");

        maskedTextField.cleanField(maskedTextField.getProjectCode());
        page.keyboard().type("!@#-001", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getProjectCode()).hasValue("   -   ");

    }
}