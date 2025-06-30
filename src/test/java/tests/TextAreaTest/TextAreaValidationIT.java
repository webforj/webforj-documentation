package tests.TextAreaTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.PlaywrightException;

import pages.TextAreaPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TextAreaValidationIT extends BaseTest {

    private TextAreaPage textAreaPage;

    @BeforeEach
    public void setupTextAreaValidation() {
        page.navigate("https://docs.webforj.com/webforj/textareavalidation?");
        textAreaPage = new TextAreaPage(page);
    }

    @BrowserTest
    public void testInitialContent() {
        String textAreaValue = textAreaPage.getValidationTextArea().inputValue();

        assertTrue(textAreaValue.contains("The quick brown fox jumps over the lazy dog."));
    }

    @BrowserTest
    public void testEdgeValues() {
        try {
            textAreaPage.getMaxLinesInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaPage.getMaxLinesInput().fill("2");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        textAreaPage.getValidationTextArea().click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Delete");
        // 1st line
        textAreaPage.getValidationTextArea().fill("Hello");
        page.keyboard().press("Enter");
        // 2nd line
        textAreaPage.getValidationTextArea().type("Hello");
        page.keyboard().press("Enter");
        // 3rd line
        textAreaPage.getValidationTextArea().type("Hello");

        assertThat(textAreaPage.getValidationTextArea()).hasValue("Hello\nHelloHello");

    }

    @BrowserTest
    public void testNegativeNumber() {
        try {
            textAreaPage.getMaxLengthInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaPage.getMaxLengthInput().fill("-5");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        assertThat(textAreaPage.getMaxLengthField()).hasAttribute("invalid", "");
    }

    @BrowserTest
    public void testInvalidCharsHandled() {
        textAreaPage.getValidationTextArea().click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Delete");

        textAreaPage.getValidationTextArea().fill("Hello");

        try {
            textAreaPage.getMaxLengthInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaPage.getMaxLengthInput().fill("Hi");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        try {
            textAreaPage.getMaxLinesInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaPage.getMaxLinesInput().fill("!@#$");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        try {
            textAreaPage.getMaxParagraphLengthInput().click();
            page.keyboard().press("Control+A");
            page.keyboard().press("Delete");
            textAreaPage.getMaxParagraphLengthInput().fill(" ");
        } catch (PlaywrightException e) {
            System.err.println("Input handling failed: " + e.getMessage());
        }

        assertThat(textAreaPage.getValidationTextArea()).hasValue("Hello");

    }
} 