package tests.FieldTest.MaskedNumberFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Keyboard;

import pages.FieldPages.MaskedNumberField.MaskedNumberFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedNumNegetableViewIT extends BaseTest {

    private MaskedNumberFieldPage numberPage;

    @BeforeEach
    public void setupMaskedNumberFieldWithNegetable() {
        navigateToRoute(MaskedNumberFieldPage.getRouteNegatable());
        numberPage = new MaskedNumberFieldPage(page);

    }

    @BrowserTest
    public void testNegetableOn() {
        assumeFalse("webkit".equals(page.context().browser().browserType().name()),
                "Skipped on WebKit (Safari verified manually)");

        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("-123", new Keyboard.TypeOptions().setDelay(300));
        assertThat(numberPage.getCreditField()).hasValue("-$123.00");

        page.mouse().click(0, 0);
        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("123", new Keyboard.TypeOptions().setDelay(250));
        assertThat(numberPage.getCreditField()).hasValue("$123.00");

        page.mouse().click(0, 0);
        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("0", new Keyboard.TypeOptions().setDelay(250));
        assertThat(numberPage.getCreditField()).hasValue("$0.00");

    }

    @BrowserTest
    public void testInvalidCharsWhenNegetableOnAndOff() {
        //negetable off
        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("Aa!@. ");
        assertThat(numberPage.getCreditField()).hasValue("");

        //negetable on
        numberPage.getNegatableToggle().click();
        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("-");
        page.keyboard().type("1");
        assertThat(numberPage.getCreditField()).hasValue("$1.00");
    }

    @BrowserTest
    public void testZeroWhenNegetableOff() {
        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("0");
        assertThat(numberPage.getCreditField()).hasValue("$0.00");
    }

    @BrowserTest
    public void testSpecialCharsWhenToggleOff() {
        numberPage.getNegatableToggle().click();

        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("Aa!@. ");

        assertThat(numberPage.getCreditField()).hasValue("");
    }

    @BrowserTest
    public void testMultipleMinusSigns() {

        numberPage.cleanField(numberPage.getCreditField());
        page.keyboard().type("---");

        assertThat(numberPage.getCreditField()).hasValue("");

        numberPage.getNegatableToggle().click();

        page.keyboard().type("-");

        assertThat(numberPage.getCreditField()).hasValue("");
    }
}