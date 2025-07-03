package tests.FieldTest.MaskedTextFieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Keyboard;

import pages.FieldPages.MaskedTextField.MaskedTextFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class MaskedTextFieldTestsIT extends BaseTest {

    private MaskedTextFieldPage maskedTextField;

    @BeforeEach
    void setupMaskedTextFieldTests() {
        if (context != null) {
            context.close();
        }

        // Only add clipboard permissions for Chromium
        if ("chromium".equals(browser.browserType().name())) {
            context = browser.newContext(new Browser.NewContextOptions()
                    .setPermissions(Arrays.asList("clipboard-read", "clipboard-write")));
        } else {
            context = browser.newContext();
        }

        page = context.newPage();
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
    void testCopyPasteValidation() {
        assumeTrue("chromium".equals(browser.browserType().name()),
                "Clipboard operations only supported in Chromium");

        maskedTextField.cleanField(maskedTextField.getCouponCode());
        maskedTextField.copyToClipboard("ABCD1234");
        maskedTextField.pasteFromClipboard(maskedTextField.getCouponCode());
        assertThat(maskedTextField.getCouponCode()).hasValue("ABCD-1234");

        maskedTextField.cleanField(maskedTextField.getRecordCode());
        maskedTextField.copyToClipboard("AA121131");
        maskedTextField.pasteFromClipboard(maskedTextField.getRecordCode());
        assertThat(maskedTextField.getRecordCode()).hasValue("AA-12-1131");
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