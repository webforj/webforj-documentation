package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.webforj.samples.utils.ThemeUtils.assertForEach;
import static com.webforj.samples.utils.ThemeUtils.assertTheme;

import com.webforj.component.Theme;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertThemesPage;
import com.webforj.samples.utils.ThemeUtils;
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

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertThemeIsVisible(SupportedLanguage language) {
    setupAlertThemesDemo(language);
    assertForEach(theme -> assertThat(alertPage.getAlert(theme)).isVisible());
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertHasCorrectTheme(SupportedLanguage language) {
    setupAlertThemesDemo(language);
    assertForEach(theme -> assertTheme(alertPage.getAlert(theme), theme));
  }

}

