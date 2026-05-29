package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonTextPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RadioButtonTextViewIT extends BaseTest {

  private RadioButtonTextPage radioButton;

  public void setupRadioButtonText(SupportedLanguage language) {
    navigateToRoute(RadioButtonTextPage.getRoute(language));
    radioButton = new RadioButtonTextPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testLeftAlignedButtonStyleIsChecked(SupportedLanguage language) {
    setupRadioButtonText(language);
    radioButton.getLeftAlignedInput().check();
    assertThat(radioButton.getLeftAlignedInput()).hasAttribute("aria-checked", "true");
  }
}
