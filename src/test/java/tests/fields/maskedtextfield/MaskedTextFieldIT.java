package tests.fields.maskedtextfield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Keyboard;

import pages.fields.maskedtextfield.MaskedTextFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedTextFieldIT extends BaseTest {

    private MaskedTextFieldPage maskedTextField;

    @BeforeEach
    void setupMaskedTextFieldTests() {
        navigateToRoute(MaskedTextFieldPage.getRouteView());
        maskedTextField = new MaskedTextFieldPage(page);
    }

    @BrowserTest
    void testInputAcceptsCharactersPerMask() {

        maskedTextField.cleanField(maskedTextField.getCouponCode());
        page.keyboard().type("ABCD-1234", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getCouponCode()).hasValue("ABCD-1234");

        maskedTextField.cleanField(maskedTextField.getRecordCode());
        page.keyboard().type("AA-12-1131", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getRecordCode()).hasValue("AA-12-1131");

    }

    @BrowserTest
    void testLowercaseToUppercaseConversion() {
        maskedTextField.cleanField(maskedTextField.getCouponCode());
        page.keyboard().type("abcd-1234", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getCouponCode()).hasValue("ABCD-1234");

        maskedTextField.cleanField(maskedTextField.getRecordCode());
        page.keyboard().type("aa-12-1131", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getRecordCode()).hasValue("AA-12-1131");
    }

    @BrowserTest
    void testOnlyDigitsAcceptedForDigitMask() {
        maskedTextField.cleanField(maskedTextField.getCouponCode());
        page.keyboard().type("ZZZZ-Z! y", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getCouponCode()).hasValue("ZZZZ-    ");

        maskedTextField.cleanField(maskedTextField.getRecordCode());
        page.keyboard().type("AA-xx-!@#$", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getRecordCode()).hasValue("AA-  -    ");
    }

    @BrowserTest
    void testAlphanumericAcceptedForZMask() {
        maskedTextField.cleanField(maskedTextField.getCouponCode());
        page.keyboard().type("ST10-2025", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getCouponCode()).hasValue("ST10-2025");
    }

    @BrowserTest
    void testMaximumInputLengthEnforced() {
        // Coupon code mask = "AAAA-9999" → max length = 9 characters
        maskedTextField.cleanField(maskedTextField.getCouponCode());
        page.keyboard().type("ABCD-123456", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getCouponCode()).hasValue(Pattern.compile("^.{9}$"));

        // Record code mask = "AA-00-0000" → max length = 10 characters
        maskedTextField.cleanField(maskedTextField.getRecordCode());
        page.keyboard().type("AA-12-1131999", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getRecordCode()).hasValue(Pattern.compile("^.{10}$"));
    }

    @BrowserTest
    void testSpecialCharactersRejected() {
        maskedTextField.cleanField(maskedTextField.getCouponCode());
        page.keyboard().type("!@#$-!@#$", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getCouponCode()).hasValue("    -    ");

        maskedTextField.cleanField(maskedTextField.getRecordCode());
        page.keyboard().type("$%-&!-****", new Keyboard.TypeOptions().setDelay(300));
        assertThat(maskedTextField.getRecordCode()).hasValue("  -  -    ");
    }

    @BrowserTest
    void testBehaviorOnFullDeletion() {
        maskedTextField.cleanField(maskedTextField.getCouponCode());
        assertThat(maskedTextField.getCouponCode()).hasValue("    -    ");

        maskedTextField.cleanField(maskedTextField.getRecordCode());
        assertThat(maskedTextField.getRecordCode()).hasValue("  -  -    ");
    }
}