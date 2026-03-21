package com.webforj.samples.views.alert;

import com.webforj.component.Theme;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertThemesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AlertThemesViewIT extends BaseTest {
  private AlertThemesPage alertPage;

  @BeforeEach
  public void setupAlertThemesDemo() {
    alertPage = new AlertThemesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertThemeHasCorrectTheme(SupportedLanguage language) {
    alertPage.setRoute(language);
    Theme[] themes = Theme.values();
    for (int i = 0; i < themes.length; i++) {
      alertPage.getAlertComponent(i).assertIsVisible();
      alertPage.getAlertComponent(i).assertTheme(themes[i]);
    }
  }

}

