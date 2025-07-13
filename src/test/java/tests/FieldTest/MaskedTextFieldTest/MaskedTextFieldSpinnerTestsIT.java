package tests.FieldTest.MaskedTextFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Keyboard;

import pages.FieldPages.MaskedTextField.MaskedTextFieldPage;
import tests.BaseTest;

public class MaskedTextFieldSpinnerTestsIT extends BaseTest {

    private MaskedTextFieldPage maskedTextField;

    @BeforeEach
    void setupMaskedTextFieldSpinnerTests() {
        navigateToRoute(MaskedTextFieldPage.getRouteSpinner());
        maskedTextField = new MaskedTextFieldPage(page);
    }

    @Test
    void testInitialValueAndSpinnerUpandDownCycle() {
        assertThat(maskedTextField.getProjectCode()).hasValue("PRJ-001");
        maskedTextField.getSpinnerUp().click();
        assertThat(maskedTextField.getProjectCode()).hasValue("PRJ-002");

        maskedTextField.getSpinnerDown().dblclick();
        assertThat(maskedTextField.getProjectCode()).hasValue("PRJ-004");
    }

    @Test
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