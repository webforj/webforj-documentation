package tests.FieldTest.MaskedNumberFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.MaskedNumberField.MaskedNumberFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedNumSpinnerViewIT extends BaseTest {

    private MaskedNumberFieldPage numberPage;

    @BeforeEach
    public void setupMaskedNumberFieldSpinner() {
        navigateToRoute(MaskedNumberFieldPage.getRouteSpinner());
        numberPage = new MaskedNumberFieldPage(page);

    }

    @BrowserTest
    public void testSpinnerButtonsIncrementDecrementByStep() {
        String initialValue = numberPage.getTipPercentageField().inputValue(); // 15%
        int initialValueInt = Integer.parseInt(initialValue.replaceAll("[^0-9]", ""));

        numberPage.getSpinnerUp().click();
        assertThat(numberPage.getTipPercentageField()).hasValue(String.valueOf(initialValueInt + 5) + "%");

        numberPage.getSpinnerDown().click();
        assertThat(numberPage.getTipPercentageField()).hasValue(initialValue);
    }

    @BrowserTest
    public void testKeyboardArrowKeysIncrementDecrementByStepFive() {
        String initialValue = numberPage.getTipPercentageField().inputValue();
        int initialValueInt = Integer.parseInt(initialValue.replaceAll("[^0-9]", ""));

        numberPage.getTipPercentageField().click();
        page.keyboard().press("ArrowUp");
        assertThat(numberPage.getTipPercentageField()).hasValue(String.valueOf(initialValueInt + 5) + "%");

        page.keyboard().press("ArrowDown");
        assertThat(numberPage.getTipPercentageField()).hasValue(initialValue);
    }

    @BrowserTest
    public void testValidNumericInputWithinMinMaxAccepted() {

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("5");
        page.keyboard().type("0");

        assertThat(numberPage.getTipPercentageField()).hasValue("50%");
    }

    @BrowserTest
    public void testDecimalValuesRejectedIfRestricted() {

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("1");
        page.keyboard().type(".");
        page.keyboard().type("2");

        assertThat(numberPage.getTipPercentageField()).hasValue("12%");
    }

    @BrowserTest
    public void testInvalidCharactersAreRejected() {
        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("Aa!@. ");

        assertThat(numberPage.getTipPercentageField()).hasValue("");
    }

    @BrowserTest
    public void testBoundaryBehaviorForValuesOutsideMinMax() {

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("1");
        page.keyboard().type("0");
        page.keyboard().type("1");

        assertThat(numberPage.getWarningPopover()).isVisible();

    }

    @BrowserTest
    public void testEmptyInputFieldBehavior() {
        numberPage.cleanField(numberPage.getTipPercentageField());
        assertThat(numberPage.getTipPercentageField()).hasValue("");

        numberPage.getSpinnerUp().click();
        assertThat(numberPage.getTipPercentageField()).hasValue("5%");
    }

    @BrowserTest
    public void testDefaultStepSizeAndDynamicStepChange() {
        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("1");
        page.keyboard().type("3");
        numberPage.getSpinnerUp().click();
        assertThat(numberPage.getTipPercentageField()).hasValue("18%");

        numberPage.getSpinnerDown().click();
        assertThat(numberPage.getTipPercentageField()).hasValue("13%");
    }

    @BrowserTest
    public void testSpinnerPreventsValueAboveMax() {

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("1");
        page.keyboard().type("0");
        page.keyboard().type("0");

        numberPage.getSpinnerUp().click();
        assertThat(numberPage.getTipPercentageField()).hasValue("100%");
    }

    @BrowserTest
    public void testMaskAppliedCorrectlyOnManualAndSpinnerInput() {

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("1");
        page.keyboard().type("0");
        page.keyboard().type("0");
        page.keyboard().type("0");

        assertThat(numberPage.getTipPercentageField()).hasValue("100%");
    }
}