package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.button.ButtonTheme;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.button.ButtonThemesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonThemesViewIT extends BaseTest {
  private ButtonThemesPage buttonPage;

  @BeforeEach
  public void setupButtonThemesDemo() {
    buttonPage = new ButtonThemesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSolidButtonsAreVisible(SupportedLanguage language) {
    buttonPage.setRoute(language);
    for (ButtonTheme theme : ButtonTheme.values()) {
      if (!theme.name().contains("OUTLINE")) {
        var button = buttonPage.getSolidButton(theme.name());
        button.assertThat().isVisible();
        button.assertThat().hasTheme(theme);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testOutlinedButtonsAreVisible(SupportedLanguage language) {
    buttonPage.setRoute(language);
    for (ButtonTheme theme : ButtonTheme.values()) {
      if (!theme.name().contains("OUTLINE")) {
        var button = buttonPage.getOutlinedButton(theme.name());
        button.assertThat().isVisible();
      }
    }
  }

}
