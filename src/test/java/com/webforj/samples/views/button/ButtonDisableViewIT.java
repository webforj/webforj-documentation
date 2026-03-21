package com.webforj.samples.views.button;

import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.button.ButtonDisablePage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonDisableViewIT extends BaseTest {

  private ButtonDisablePage buttonDisablePage;

  @BeforeEach
  public void setupButtonDisableDemo() {
    buttonDisablePage = new ButtonDisablePage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSubmitButtonIsDisabledWhenEmailInputIsNotValid(SupportedLanguage language) {
    buttonDisablePage.setRoute(language);
    buttonDisablePage.getEmailInput().fill("invalid-email");
    buttonDisablePage.getSubmitButton().assertDisabled();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSubmitButtonIsEnabledWhenEmailInputIsValid(SupportedLanguage language) {
    buttonDisablePage.setRoute(language);
    buttonDisablePage.getEmailInput().fill("valid-email@example.com");
    buttonDisablePage.getSubmitButton().assertEnabled();
  }

}
