package tests.TextAreaTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TextAreaPage.FeedbackTextAreaPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FeedbackTextAreaIT extends BaseTest {

    private FeedbackTextAreaPage feedbackTextAreaPage;

    @BeforeEach
    public void setupFeedbackTextArea() {
        navigateToRoute(FeedbackTextAreaPage.getRoute());
        feedbackTextAreaPage = new FeedbackTextAreaPage(page);
    }

    @BrowserTest
    public void testVisibility() {
        assertThat(feedbackTextAreaPage.getMainLabel()).isVisible();
        assertThat(feedbackTextAreaPage.getMainLabel()).containsText("What do you think about this demo?");
        assertThat(feedbackTextAreaPage.getFeedbackArea()).isVisible();
        assertThat(feedbackTextAreaPage.getCharactersCount()).isVisible();
        assertThat(feedbackTextAreaPage.getCharactersCount()).containsText("Characters: 0 / 200");
        assertThat(feedbackTextAreaPage.getSubmitButton()).isVisible();
    }

    @BrowserTest
    public void testToastMessage() {
        feedbackTextAreaPage.getFeedbackArea().fill("Hello World");
        assertThat(feedbackTextAreaPage.getCharactersCount()).containsText("11 / 200");

        feedbackTextAreaPage.getSubmitButton().click();
        assertThat(feedbackTextAreaPage.getDonationToaster()).isVisible();

    }

    @BrowserTest
    public void testCharacterLimit() {
        // "HelloWorld" repeated 21 times is 210 characters, but we still expect the
        // result to be 200 characters.
        for (int i = 0; i < 22; i++) {
            feedbackTextAreaPage.getFeedbackArea().type("Hello World");
        }
        assertThat(feedbackTextAreaPage.getCharactersCount()).containsText("200 / 200");

    }

    @BrowserTest
    public void testEmptyFeedback() {
        feedbackTextAreaPage.getFeedbackArea().fill("");
        assertThat(feedbackTextAreaPage.getCharactersCount()).containsText("0 / 200");

        feedbackTextAreaPage.getSubmitButton().click();
        assertThat(feedbackTextAreaPage.getDonationToaster()).not().isVisible();

    }
}