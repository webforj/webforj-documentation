package com.webforj.samples.views.textarea;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.textarea.TextAreaPage;
import com.webforj.samples.views.BaseTest;

public class TextAreaIT extends BaseTest {

    private TextAreaPage feedbackTextAreaPage;

    @BeforeEach
    public void setupFeedbackTextArea() {
        navigateToRoute(TextAreaPage.getRoute());
        feedbackTextAreaPage = new TextAreaPage(page);
    }

    @Test
    public void testToastMessage() {
        feedbackTextAreaPage.getFeedbackArea().fill("Hello World");
        
        feedbackTextAreaPage.getSubmitButton().click();
        assertThat(feedbackTextAreaPage.getDonationToaster()).isVisible();

    }

    @Test
    public void testCharacterLimit() {
        // "HelloWorld" repeated 21 times is 210 characters, but we still expect the
        // result to be 200 characters.
        for (int i = 0; i < 22; i++) {
            feedbackTextAreaPage.getFeedbackArea().pressSequentially("Hello World");
        }
        assertThat(feedbackTextAreaPage.getCharactersCount()).containsText("200 / 200");

    }

    @Test
    public void testEmptyFeedback() {
        feedbackTextAreaPage.getSubmitButton().click();
        assertThat(feedbackTextAreaPage.getDonationToaster()).not().isVisible();

    }
}
