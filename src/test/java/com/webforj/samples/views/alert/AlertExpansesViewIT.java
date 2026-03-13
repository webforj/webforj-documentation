package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.webforj.samples.utils.ExpanseUtils.assertExpanse;
import static com.webforj.samples.utils.ExpanseUtils.assertForEach;
import static com.webforj.samples.utils.ThemeUtils.assertTheme;

import com.webforj.component.Theme;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertExpansesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AlertExpansesViewIT extends BaseTest {
  private AlertExpansesPage alertPage;

  public void setupAlertExpansesDemo(SupportedLanguage language) {
    navigateToRoute(AlertExpansesPage.getRoute(language));
    alertPage = new AlertExpansesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertExpanseIsVisible(SupportedLanguage language) {
    setupAlertExpansesDemo(language);
    assertForEach(expanse -> assertThat(alertPage.getAlert(expanse)).isVisible());
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertHasCorrectExpanse(SupportedLanguage language) {
    setupAlertExpansesDemo(language);
    assertForEach(expanse -> assertExpanse(alertPage.getAlert(expanse), expanse));
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertsHaveSuccessTheme(SupportedLanguage language) {
    setupAlertExpansesDemo(language);
    assertForEach(expanse -> assertTheme(alertPage.getAlert(expanse), Theme.SUCCESS));
  }

}
