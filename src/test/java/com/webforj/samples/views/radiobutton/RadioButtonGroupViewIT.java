package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonGroupPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RadioButtonGroupViewIT extends BaseTest {

  private RadioButtonGroupPage radioButtonGroupPage;

  public void setupRadioButtonGroup(SupportedLanguage language) {
    navigateToRoute(RadioButtonGroupPage.getRoute(language));
    radioButtonGroupPage = new RadioButtonGroupPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testStronglyDisagreeRadioButtonGroupIsChecked(SupportedLanguage language) {
    setupRadioButtonGroup(language);
    radioButtonGroupPage.getStronglyDisagreeRB().check();
    assertThat(radioButtonGroupPage.getStronglyDisagreeRB()).isChecked();

    radioButtonGroupPage.getDisagreeRB().check();
    assertThat(radioButtonGroupPage.getDisagreeRB()).isChecked();
    assertThat(radioButtonGroupPage.getStronglyDisagreeRB()).not().isChecked();
  }
}
