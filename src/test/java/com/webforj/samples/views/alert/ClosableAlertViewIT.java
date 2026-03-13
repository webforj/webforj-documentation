package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.ClosableAlertPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ClosableAlertViewIT extends BaseTest {

  private ClosableAlertPage alertPage;

  public void setupClosableAlertDemo(SupportedLanguage language) {
    navigateToRoute(ClosableAlertPage.getRoute(language));
    alertPage = new ClosableAlertPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertIsVisible(SupportedLanguage language) {
    setupClosableAlertDemo(language);
    assertThat(alertPage.getAlert()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertTextIsVisible(SupportedLanguage language) {
    setupClosableAlertDemo(language);
    assertThat(alertPage.getAlertText()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCloseButtonIsVisible(SupportedLanguage language) {
    setupClosableAlertDemo(language);
    assertThat(alertPage.getCloseButton()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testShowAlertButtonIsHiddenInitially(SupportedLanguage language) {
    setupClosableAlertDemo(language);
    assertThat(alertPage.getShowAlertButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertHasInfoTheme(SupportedLanguage language) {
    setupClosableAlertDemo(language);
    assertThat(alertPage.getAlert()).hasAttribute("theme", "info");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCloseButtonDismissesAlert(SupportedLanguage language) {
    setupClosableAlertDemo(language);
    assertThat(alertPage.getAlert()).isVisible();

    alertPage.getCloseButton().click();

    assertThat(alertPage.getAlert()).not().isVisible();
    assertThat(alertPage.getShowAlertButton()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testShowAlertButtonReopensAlert(SupportedLanguage language) {
    setupClosableAlertDemo(language);

    // Close the alert first
    alertPage.getCloseButton().click();
    assertThat(alertPage.getAlert()).not().isVisible();
    assertThat(alertPage.getShowAlertButton()).isVisible();

    // Reopen the alert
    alertPage.getShowAlertButton().click();

    assertThat(alertPage.getAlert()).isVisible();
    assertThat(alertPage.getShowAlertButton()).not().isVisible();
  }
}
