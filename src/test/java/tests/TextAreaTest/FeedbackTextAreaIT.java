package tests.TextAreaTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TextAreaPage;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FeedbackTextAreaIT extends BaseTest {

    private TextAreaPage textAreaPage;

    @BeforeEach
    public void setupFeedbackTextArea() {
        page.navigate("https://docs.webforj.com/webforj/textarea?");
        textAreaPage = new TextAreaPage(page);
    }

    @BrowserTest
    public void testVisibility() {
        assertThat(textAreaPage.getMainLabel()).isVisible();
        assertThat(textAreaPage.getMainLabel()).containsText("What do you think about this demo?");
        assertThat(textAreaPage.getFeedbackArea()).isVisible();
        assertThat(textAreaPage.getCharactersCount()).isVisible();
        assertThat(textAreaPage.getCharactersCount()).containsText("Characters: 0 / 200");
        assertThat(textAreaPage.getSubmitButton()).isVisible();
    }

    @BrowserTest
    public void testToastMessage() {
        textAreaPage.getFeedbackArea().fill("Hello World");
        assertThat(textAreaPage.getCharactersCount()).containsText("11 / 200");

        textAreaPage.getSubmitButton().click();
        assertThat(textAreaPage.getDonationToaster()).isVisible();

    }

    @BrowserTest
    public void testCharacterLimit() {
        // "HelloWorld" repeated 21 times is 210 characters, but we still expect the
        // result to be 200 characters.
        for (int i = 0; i < 22; i++) {
            textAreaPage.getFeedbackArea().type("Hello World");
        }
        assertThat(textAreaPage.getCharactersCount()).containsText("200 / 200");

    }

    @BrowserTest
    public void testEmptyFeedback() {
        textAreaPage.getFeedbackArea().fill(""); 
        assertThat(textAreaPage.getCharactersCount()).containsText("0 / 200");

        textAreaPage.getSubmitButton().click();
        assertThat(textAreaPage.getDonationToaster()).not().isVisible();

    }
} 