package tests.MaskedFields.MaskedNumberFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Keyboard;

import pages.MaskedFields.MaskedNumberFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedNumberFieldWithNegetableIT extends BaseTest {

    private MaskedNumberFieldPage numberPage;

    @BeforeEach
    public void setupMaskedNumberFieldWithNegetable() {
        page.navigate("https://docs.webforj.com/webforj/maskednumnegatable/?");
        numberPage = new MaskedNumberFieldPage(page);

    }

    @BrowserTest
    public void testNegetableOn() {
        assumeFalse("webkit".equals(page.context().browser().browserType().name()),
                "Skipped on WebKit (Safari verified manually)");

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("-123", new Keyboard.TypeOptions().setDelay(300));
        assertThat(numberPage.getAmountField()).hasValue("-$123.00");

        page.mouse().click(0, 0);
        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("123", new Keyboard.TypeOptions().setDelay(250));
        assertThat(numberPage.getAmountField()).hasValue("$123.00");

        page.mouse().click(0, 0);
        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("0", new Keyboard.TypeOptions().setDelay(250));
        assertThat(numberPage.getAmountField()).hasValue("$0.00");

    }

    @BrowserTest
    public void testInvalidCharsWhenNegetableOn() {

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("Aa!@. ");

        assertThat(numberPage.getAmountField()).hasValue("");
    }

    @BrowserTest
    public void testNegativeNumbersWhenNegetableOff() {

        numberPage.getNegatableToggle().click();
        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("-");
        page.keyboard().type("1");
        assertThat(numberPage.getAmountField()).hasValue("$1.00");
    }

    @BrowserTest
    public void testZeroWhenNegetableOff() {
        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("0");
        assertThat(numberPage.getAmountField()).hasValue("$0.00");
    }

    @BrowserTest
    public void testSpecialCharsWhenToggleOff() {
        numberPage.getNegatableToggle().click();

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("Aa!@. ");

        assertThat(numberPage.getAmountField()).hasValue("");
    }

    @BrowserTest
    public void testMultipleMinusSigns() {

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("---");

        assertThat(numberPage.getAmountField()).hasValue("");

        numberPage.getNegatableToggle().click();

        page.keyboard().type("-");

        assertThat(numberPage.getAmountField()).hasValue("");
    }
} 