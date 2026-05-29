package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.button.ButtonPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonViewIT extends BaseTest {

  private ButtonPage button;

  public void setupButtonDemo(SupportedLanguage language) {
    navigateToRoute(ButtonPage.getRoute(language));
    button = new ButtonPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testWelcomeMessageIsDisplayedWhenSubmitButtonIsClicked(SupportedLanguage language) {
    setupButtonDemo(language);
    button.getSubmitButton().click();
    assertThat(button.getWelcomeDialog()).isVisible();

  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInputsAreClearedWhenClearButtonIsClicked(SupportedLanguage language) {
    setupButtonDemo(language);
    assertThat(button.getFirstName()).hasValue("Jason");
    assertThat(button.getLastName()).hasValue("Turner");
    assertThat(button.getEmail()).hasValue("turner.jason@email.com");

    button.getClearButton().click();

    assertThat(button.getFirstName()).hasValue("");
    assertThat(button.getLastName()).hasValue("");
    assertThat(button.getEmail()).hasValue("");

  }
}
