package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertThemesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AlertThemesViewIT extends BaseTest {

  private AlertThemesPage alertThemesPage;

  public void setupAlertThemes(SupportedLanguage language) {
    navigateToRoute(AlertThemesPage.getRoute(language));
    alertThemesPage = new AlertThemesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertThemes(SupportedLanguage language) {
    setupAlertThemes(language);
    assertThat(alertThemesPage.getSuccessAlert()).hasAttribute("theme", "success");
  }

  @Test
  public void testAlertThemes() {
    assertThat(alertThemesPage.getSuccessAlert()).hasAttribute("theme", "success");
  }
}
