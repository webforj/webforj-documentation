package com.webforj.samples.views.textarea;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

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
        feedbackTextAreaPage.getFeedbackTextarea().fill("Hello World");

        feedbackTextAreaPage.getSubmitButton().click();
        assertThat(feedbackTextAreaPage.getDonationToaster()).isVisible();

    }

    @Test
    public void testCharacterLimit() {
        // "Hello World" repeated 20 times is 220 characters, but we still expect the
        // result to be 200 characters.
        for (int i = 0; i < 20; i++) {
            feedbackTextAreaPage.getFeedbackTextarea().pressSequentially("Hello World");
        }
        assertThat(feedbackTextAreaPage.getFeedbackTextarea())
                .hasValue(Pattern.compile("^[\\s\\S]{200}$"));

    }

    @Test
    public void testEmptyFeedback() {
        feedbackTextAreaPage.getSubmitButton().click();
        assertThat(feedbackTextAreaPage.getDonationToaster()).not().isVisible();

    }
}
