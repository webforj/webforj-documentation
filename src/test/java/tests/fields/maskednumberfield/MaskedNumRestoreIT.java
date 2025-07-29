package tests.fields.maskednumberfield;
// package tests.FieldTest.MaskedNumberFieldTest;

// import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

// import org.junit.jupiter.api.BeforeEach;

// import pages.FieldPages.MaskedNumberField.MaskedNumberFieldPage;
// import tests.BaseTest;
// import utils.annotations.BrowserTest;

// public class MaskedNumRestoreIT extends BaseTest {

//     private MaskedNumberFieldPage numberPage;

//     @BeforeEach
//     public void setupMaskedNumberFieldWithRestoreValue() {
//         navigateToRoute(MaskedNumberFieldPage.getRouteRestore());
//         numberPage = new MaskedNumberFieldPage(page);
//     }

//     @BrowserTest
//     public void testResetValueButtonResetsToDefaultAfterNumberChange() {

//         String initialBudgetValue = numberPage.getProjectBudgetField().inputValue();

//         numberPage.cleanField(numberPage.getProjectBudgetField());
//         page.keyboard().type("123");
//         numberPage.getResetValueButton().click();
//         assertThat(numberPage.getProjectBudgetField()).hasValue(initialBudgetValue);

//     }

//     @BrowserTest
//     public void testEscKeyResetsToDefaultAfterNumberChange() {
//         String initialBudgetValue = numberPage.getProjectBudgetField().inputValue();

//         numberPage.cleanField(numberPage.getProjectBudgetField());
//         page.keyboard().type("123");

//         page.keyboard().press("Escape");

//         assertThat(numberPage.getProjectBudgetField()).hasValue(initialBudgetValue);
//     }

//     @BrowserTest
//     public void testResetValueButtonResetsToDefaultAfterNegativeNumberChange() {

//         String initialBudgetValue = numberPage.getProjectBudgetField().inputValue();

//         numberPage.cleanField(numberPage.getProjectBudgetField());
//         page.keyboard().type("-");
//         page.keyboard().type("1");
//         numberPage.getResetValueButton().click();

//         assertThat(numberPage.getProjectBudgetField()).hasValue(initialBudgetValue);
//     }

//     @BrowserTest
//     public void testResetValueButtonResetsToDefaultAfterClearingInput() {

//         String initialBudgetValue = numberPage.getProjectBudgetField().inputValue();
//         numberPage.cleanField(numberPage.getProjectBudgetField());
//         numberPage.getResetValueButton().click();

//         assertThat(numberPage.getProjectBudgetField()).hasValue(initialBudgetValue);
//     }
// }