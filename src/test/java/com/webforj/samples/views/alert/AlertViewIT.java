package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AlertViewIT extends BaseTest {

  private AlertPage alertPage;

  public void setupAlertDemo(SupportedLanguage language) {
    navigateToRoute(AlertPage.getRoute(language));
    alertPage = new AlertPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertIsVisible(SupportedLanguage language) {
    setupAlertDemo(language);
    assertThat(alertPage.getAlert()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertTextIsVisible(SupportedLanguage language) {
    setupAlertDemo(language);
    assertThat(alertPage.getAlertText()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testViewButtonIsVisible(SupportedLanguage language) {
    setupAlertDemo(language);
    assertThat(alertPage.getViewButton()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertHasPrimaryTheme(SupportedLanguage language) {
    setupAlertDemo(language);
    assertThat(alertPage.getAlert()).hasAttribute("theme", "primary");
  }
}
