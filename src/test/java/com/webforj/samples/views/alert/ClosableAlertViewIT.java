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
    alertPage.getAlert().assertThat().isVisible();
    alertPage.getAlert().assertThat().hasTheme(Theme.INFO);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertTextIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getAlertText().assertThat().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCloseButtonIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getCloseButton().assertThat().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testShowAlertButtonIsHiddenInitially(SupportedLanguage language) {
    alertPage.setRoute(language);
    assertThat(alertPage.getShowAlertButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCloseButtonDismissesAlert(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getAlert().assertThat().isVisible();
    alertPage.getCloseButton().click();
    alertPage.getAlert().assertThat().not().isVisible();
    assertThat(alertPage.getShowAlertButton()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testShowAlertButtonReopensAlert(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getAlert().assertThat().isVisible();

    // Close the alert first
    alertPage.getCloseButton().click();
    alertPage.getAlert().assertThat().not().isVisible();
    assertThat(alertPage.getShowAlertButton()).isVisible();

    // Reopen the alert
    alertPage.getShowAlertButton().click();
    alertPage.getAlert().assertThat().isVisible();
    assertThat(alertPage.getShowAlertButton()).not().isVisible();
  }
}
