package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.button.ButtonDisablePage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonDisableViewIT extends BaseTest {

  private ButtonDisablePage buttonDisable;

  public void setupButtonDisableDemo(SupportedLanguage language) {
    navigateToRoute(ButtonDisablePage.getRoute(language));
    buttonDisable = new ButtonDisablePage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSubmitButtonIsDisabledWhenEmailInputIsNotValid(SupportedLanguage language) {
    setupButtonDisableDemo(language);
    buttonDisable.getEmailInput().fill("invalid-email");
    assertThat(buttonDisable.getSubmitButton()).isDisabled();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSubmitButtonIsEnabledWhenEmailInputIsValid(SupportedLanguage language) {
    setupButtonDisableDemo(language);
    buttonDisable.getEmailInput().fill("valid-email@example.com");
    assertThat(buttonDisable.getSubmitButton()).isEnabled();
  }

}
