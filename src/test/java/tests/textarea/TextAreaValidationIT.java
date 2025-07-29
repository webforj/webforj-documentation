package tests.textarea;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.PlaywrightException;

import pages.textarea.TextAreaValidationPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TextAreaValidationIT extends BaseTest {

    private TextAreaValidationPage textAreaValidationPage;

    @BeforeEach
    public void setupTextAreaValidation() {
        navigateToRoute(TextAreaValidationPage.getRoute());
        textAreaValidationPage = new TextAreaValidationPage(page);
    }

    @BrowserTest
    public void testInitialContent() {
        String textAreaValue = textAreaValidationPage.getValidationTextArea().inputValue();

        assertTrue(textAreaValue.contains("The quick brown fox jumps over the lazy dog."));
    }

    @BrowserTest
    public void testEdgeValues() {
        try {
            textAreaValidationPage.getMaxLinesInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaValidationPage.getMaxLinesInput().fill("2");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        textAreaValidationPage.getValidationTextArea().click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Delete");
        // 1st line
        textAreaValidationPage.getValidationTextArea().fill("Hello");
        page.keyboard().press("Enter");
        // 2nd line
        textAreaValidationPage.getValidationTextArea().type("Hello");
        page.keyboard().press("Enter");
        // 3rd line
        textAreaValidationPage.getValidationTextArea().type("Hello");

        assertThat(textAreaValidationPage.getValidationTextArea()).hasValue("Hello\nHelloHello");

    }

    @BrowserTest
    public void testNegativeNumber() {
        try {
            textAreaValidationPage.getMaxLengthInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaValidationPage.getMaxLengthInput().fill("-5");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        assertThat(textAreaValidationPage.getMaxLengthField()).hasAttribute("invalid", "");
    }

    @BrowserTest
    public void testInvalidCharsHandled() {
        textAreaValidationPage.getValidationTextArea().click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Delete");

        textAreaValidationPage.getValidationTextArea().fill("Hello");

        try {
            textAreaValidationPage.getMaxLengthInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaValidationPage.getMaxLengthInput().fill("Hi");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        try {
            textAreaValidationPage.getMaxLinesInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaValidationPage.getMaxLinesInput().fill("!@#$");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        try {
            textAreaValidationPage.getMaxParagraphLengthInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaValidationPage.getMaxParagraphLengthInput().fill(" ");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        assertThat(textAreaValidationPage.getValidationTextArea()).hasValue("Hello");

    }
}