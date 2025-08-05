package com.webforj.samples.views.fields.numberfield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.PlaywrightException;

import com.webforj.samples.pages.fields.numberfield.NumberFieldPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class NumberFieldIT extends BaseTest {

    private NumberFieldPage numberFieldPage;

    @BeforeEach
    public void setupNumberField() {
        navigateToRoute(NumberFieldPage.getRoute());
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
