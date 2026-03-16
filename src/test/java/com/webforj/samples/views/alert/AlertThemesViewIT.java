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
    var themes = Theme.values();
    for (int i = 0; i < themes.length; i++) {
      var alert = alertPage.getAlert().nth(i);
      alert.assertThat().isVisible();
      alert.assertThat().hasTheme(themes[i]);
    }
  }

}

