package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonSwitchPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RadioButtonSwitchViewIT extends BaseTest {

  private RadioButtonSwitchPage radioButton;

  public void setupRadioButtonSwitch(SupportedLanguage language) {
    navigateToRoute(RadioButtonSwitchPage.getRoute(language));
    radioButton = new RadioButtonSwitchPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSwitchRadioButtonStyleIsChecked(SupportedLanguage language) {
    setupRadioButtonSwitch(language);
    radioButton.getSwitchRadio().check();

    assertThat(radioButton.getSwitchRadio()).isChecked();
  }
}
