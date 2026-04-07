package com.webforj.samples.views.textarea;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.textarea.TextAreaPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TextAreaViewIT extends BaseTest {

  private TextAreaPage feedbackTextAreaPage;

  public void setupFeedbackTextArea(SupportedLanguage language) {
    navigateToRoute(TextAreaPage.getRoute(language));
    feedbackTextAreaPage = new TextAreaPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testToastMessageIsDisplayedWhenSubmitButtonIsClicked(SupportedLanguage language) {
    setupFeedbackTextArea(language);
    feedbackTextAreaPage.getFeedbackTextarea().fill("Hello World");

    feedbackTextAreaPage.getSubmitButton().click();
    assertThat(feedbackTextAreaPage.getDonationToaster()).isVisible();

  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCharacterLimitIsEnforced(SupportedLanguage language) {
    setupFeedbackTextArea(language);
    // "Hello World" repeated 20 times is 220 characters, but we still expect the
    // result to be 200 characters.
    for (int i = 0; i < 20; i++) {
      feedbackTextAreaPage.getFeedbackTextarea().pressSequentially("Hello World");
    }
    assertThat(feedbackTextAreaPage.getFeedbackTextarea())
      .hasValue(Pattern.compile("^[\\s\\S]{200}$"));

  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testEmptyFeedbackTextAreaIsNotSubmitted(SupportedLanguage language) {
    setupFeedbackTextArea(language);
    feedbackTextAreaPage.getSubmitButton().click();
    assertThat(feedbackTextAreaPage.getDonationToaster()).not().isVisible();

  }
}
