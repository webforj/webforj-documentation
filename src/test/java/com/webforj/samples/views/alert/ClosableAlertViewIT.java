package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.Theme;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.alert.ClosableAlertPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ClosableAlertViewIT extends BaseTest {
  private ClosableAlertPage alertPage;

  @BeforeEach
  public void setupClosableAlertDemo() {
    alertPage = new ClosableAlertPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    var alert = alertPage.getAlert();
    alert.assertThat().isVisible();
    alert.assertThat().hasTheme(Theme.INFO);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertTextIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getAlertText().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCloseButtonIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getCloseButton().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testShowAlertButtonIsHiddenInitially(SupportedLanguage language) {
    alertPage.setRoute(language);
//    alertPage.getShowAlertButton().assertThat().not().isVisible();
    assertThat(alertPage.getShowAlertButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCloseButtonDismissesAlert(SupportedLanguage language) {
    alertPage.setRoute(language);
    var alert = alertPage.getAlert();

    alert.assertThat().isVisible();
    alertPage.getCloseButton().click();
    alert.assertThat().not().isVisible();

//    alertPage.getShowAlertButton().isVisible();
    assertThat(alertPage.getShowAlertButton()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testShowAlertButtonReopensAlert(SupportedLanguage language) {
    alertPage.setRoute(language);
    var alert = alertPage.getAlert();
    alert.assertThat().isVisible();

    // Close the alert first
    alertPage.getCloseButton().click();
    alert.assertThat().not().isVisible();
    var showButton = alertPage.getShowAlertButton();
    assertThat(showButton).isVisible();
//    var showButton = alertPage.getShowAlertButton();
//    showButton.assertThat().isVisible();

    // Reopen the alert
    showButton.click();
    alert.assertThat().isVisible();

//    showButton.assertThat().not().isVisible();
    assertThat(showButton).not().isVisible();
  }
}
