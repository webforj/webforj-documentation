package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.Theme;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertThemesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

public class AlertThemesViewIT extends BaseTest {

  private AlertThemesPage alertPage;

  public void setupAlertThemesDemo(SupportedLanguage language) {
    navigateToRoute(AlertThemesPage.getRoute(language));
    alertPage = new AlertThemesPage(page);
  }

  static Stream<Arguments> provideLanguageAndTheme() {
    List<Arguments> arguments = new ArrayList<>();
    for (SupportedLanguage language : SupportedLanguage.values()) {
      for (Theme theme : Theme.values()) {
        arguments.add(Arguments.of(language, theme));
      }
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideLanguageAndTheme")
  public void testAlertThemeIsVisible(SupportedLanguage language, Theme theme) {
    setupAlertThemesDemo(language);

    switch (theme) {
      case DEFAULT:
        assertThat(alertPage.getDefaultAlert()).isVisible();
        break;
      case PRIMARY:
        assertThat(alertPage.getPrimaryAlert()).isVisible();
        break;
      case SUCCESS:
        assertThat(alertPage.getSuccessAlert()).isVisible();
        break;
      case WARNING:
        assertThat(alertPage.getWarningAlert()).isVisible();
        break;
      case DANGER:
        assertThat(alertPage.getDangerAlert()).isVisible();
        break;
      case INFO:
        assertThat(alertPage.getInfoAlert()).isVisible();
        break;
    }
  }

  @ParameterizedTest
  @MethodSource("provideLanguageAndTheme")
  public void testAlertHasCorrectTheme(SupportedLanguage language, Theme theme) {
    setupAlertThemesDemo(language);

    var themeName = theme.name().toLowerCase();
    switch (theme) {
      case DEFAULT:
        assertThat(alertPage.getDefaultAlert()).hasAttribute("theme", themeName);
        break;
      case PRIMARY:
        assertThat(alertPage.getPrimaryAlert()).hasAttribute("theme", themeName);
        break;
      case SUCCESS:
        assertThat(alertPage.getSuccessAlert()).hasAttribute("theme", themeName);
        break;
      case WARNING:
        assertThat(alertPage.getWarningAlert()).hasAttribute("theme", themeName);
        break;
      case DANGER:
        assertThat(alertPage.getDangerAlert()).hasAttribute("theme", themeName);
        break;
      case INFO:
        assertThat(alertPage.getInfoAlert()).hasAttribute("theme", themeName);
        break;
    }
  }
}
