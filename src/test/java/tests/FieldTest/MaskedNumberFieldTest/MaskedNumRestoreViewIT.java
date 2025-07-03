package tests.FieldTest.MaskedNumberFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.MaskedNumberField.MaskedNumberFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedNumRestoreViewIT extends BaseTest {

    private MaskedNumberFieldPage numberPage;

    @BeforeEach
    public void setupMaskedNumberFieldWithRestoreValue() {
        navigateToRoute(MaskedNumberFieldPage.getRouteRestore());
        numberPage = new MaskedNumberFieldPage(page);
    }

    @BrowserTest
    public void testResetValueButtonResetsToDefaultAfterNumberChange() {

        String initialBudgetValue = numberPage.getAmountField().inputValue();

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("123");
        numberPage.getResetValueButton().click();
        assertThat(numberPage.getAmountField()).hasValue(initialBudgetValue);

    }

    @BrowserTest
    public void testEscKeyResetsToDefaultAfterNumberChange() {
        String initialBudgetValue = numberPage.getAmountField().inputValue();

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("123");

        page.keyboard().press("Escape");

        assertThat(numberPage.getAmountField()).hasValue(initialBudgetValue);
    }

    @BrowserTest
    public void testResetValueButtonResetsToDefaultAfterNegativeNumberChange() {

        String initialBudgetValue = numberPage.getAmountField().inputValue();

        numberPage.cleanField(numberPage.getAmountField());
        page.keyboard().type("-");
        page.keyboard().type("1");
        numberPage.getResetValueButton().click();

        assertThat(numberPage.getAmountField()).hasValue(initialBudgetValue);
    }

    @BrowserTest
    public void testResetValueButtonResetsToDefaultAfterClearingInput() {

        String initialBudgetValue = numberPage.getAmountField().inputValue();
        numberPage.cleanField(numberPage.getAmountField());
        numberPage.getResetValueButton().click();

        assertThat(numberPage.getAmountField()).hasValue(initialBudgetValue);
    }
}