package tests.FieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.PlaywrightException;

import pages.FieldPages.NumberFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class NumberFieldIT extends BaseTest {

    private NumberFieldPage numberFieldPage;

    @BeforeEach
    public void setupNumberField() {
        page.navigate("https://docs.webforj.com/numberfield?");
        numberFieldPage = new NumberFieldPage(page);
    }

    @BrowserTest
    public void testNumericNumber() {
        numberFieldPage.getNumberField().fill("1234567890");
        assertThat(numberFieldPage.getNumberField()).hasValue("1234567890");
    }

    @BrowserTest
    public void testNonNumericNumber() {
        try {
            numberFieldPage.getNumberField().fill("abcd");
        } catch (PlaywrightException e) {
        }
        assertThat(numberFieldPage.getNumberField()).hasValue("");

        try {
            numberFieldPage.getNumberField().fill("!#$%");
        } catch (PlaywrightException e) {
        }
        assertThat(numberFieldPage.getNumberField()).hasValue("");
    }
} 