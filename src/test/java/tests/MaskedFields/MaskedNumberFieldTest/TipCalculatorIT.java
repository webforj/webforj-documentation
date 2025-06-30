package tests.MaskedFields.MaskedNumberFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Keyboard;

import pages.MaskedFields.MaskedNumberFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TipCalculatorIT extends BaseTest {

    private MaskedNumberFieldPage numberPage;

    @BeforeEach
    public void setupTipCalculator() {
        page.navigate("https://docs.webforj.com/webforj/maskednumberfield?");
        numberPage = new MaskedNumberFieldPage(page);
    }

    @BrowserTest
    public void testValidMonetaryInput() {
        assumeFalse("webkit".equals(page.context().browser().browserType().name()),
                "Skipped on WebKit (Safari verified manually)");

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("100", new Keyboard.TypeOptions().setDelay(250));
        numberPage.getCalculateTipButton().click();
        assertThat(numberPage.getToast()).hasText("Tip: $15.00 Total: $115.00");

    }

    @BrowserTest
    public void testDecimalInput() {
        assumeFalse("webkit".equals(page.context().browser().browserType().name()),
                "Skipped on WebKit (Safari verified manually)");

        numberPage.cleanField(numberPage.getAmountField());
        page.waitForTimeout(300);
        page.keyboard().type("1.2", new Keyboard.TypeOptions().setDelay(250));
        numberPage.getCalculateTipButton().click();

        assertThat(numberPage.getToast()).containsText("Tip: $0.18");
        assertThat(numberPage.getToast()).containsText("Total: $1.38");

    }

    @BrowserTest
    public void testMaximumValue() {

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("999999999");
        numberPage.getCalculateTipButton().click();

        assertThat(numberPage.getAmountField()).hasValue("$999999.00");

    }

    @BrowserTest
    public void testNegativeInput() {

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("-5");
        numberPage.getCalculateTipButton().click();

        assertThat(numberPage.getToastTheme()).hasAttribute("theme", "danger");

    }

    @BrowserTest
    public void testZeroTip() {

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("0");
        numberPage.getCalculateTipButton().click();

        assertThat(numberPage.getToastTheme()).hasAttribute("theme", "danger");

    }

    @BrowserTest
    public void testZeroBillAmount() {

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("0");
        numberPage.getCalculateTipButton().click();

        assertThat(numberPage.getToastTheme()).hasAttribute("theme", "danger");
    }

    @BrowserTest
    public void testSpecialCharacters() {

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("Aa!@. ");

        numberPage.cleanField(numberPage.getTipPercentageField());
        page.keyboard().type("Aa!@. ");
        numberPage.getCalculateTipButton().click();

        assertThat(numberPage.getTipPercentageField()).hasValue("");
        assertThat(numberPage.getAmountField()).hasValue("");
        assertThat(numberPage.getToastTheme()).hasAttribute("theme", "danger");

    }

    @BrowserTest
    public void testEmptyInput() {

        numberPage.cleanField(numberPage.getAmountField());
        numberPage.cleanField(numberPage.getTipPercentageField());
        numberPage.getCalculateTipButton().click();

        assertThat(numberPage.getTipPercentageField()).hasValue("");
        assertThat(numberPage.getAmountField()).hasValue("");
        assertThat(numberPage.getToastTheme()).hasAttribute("theme", "danger");

    }
} 