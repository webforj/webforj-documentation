package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.button.ButtonEventPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonEventViewIT extends BaseTest {

  private ButtonEventPage buttonEventPage;

  public void setupButtonEventDemo(SupportedLanguage language) {
    navigateToRoute(ButtonEventPage.getRoute(language));
    buttonEventPage = new ButtonEventPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testButtonEventIsTriggeredWhenButtonIsClicked(SupportedLanguage language) {
    setupButtonEventDemo(language);
    buttonEventPage.getButton().click();
    assertThat(buttonEventPage.getCounterText("1")).isVisible();
  }

}
