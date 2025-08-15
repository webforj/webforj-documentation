package com.webforj.samples.views.fields.numberfield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.PlaywrightException;

import com.webforj.samples.pages.fields.numberfield.NumberFieldPage;
import com.webforj.samples.views.BaseTest;

public class NumberFieldIT extends BaseTest {

    private NumberFieldPage numberFieldPage;

    @BeforeEach
    public void setupNumberField() {
        navigateToRoute(NumberFieldPage.getRoute());
        numberFieldPage = new NumberFieldPage(page);
    }

    @Test
    public void testNumericNumber() {
        numberFieldPage.getNumberField().fill("1234567890");
        assertThat(numberFieldPage.getNumberField()).hasValue("1234567890");
    }

    @Test
    public void testNonNumericNumber() {
        try {
            numberFieldPage.getNumberField().fill("abcd");
        } 
        catch (PlaywrightException e) {
        }
        assertThat(numberFieldPage.getNumberField()).hasValue("");

        try {
            numberFieldPage.getNumberField().fill("!#$%");
        } catch (PlaywrightException e) {
        }
        assertThat(numberFieldPage.getNumberField()).hasValue("");
    }
}
