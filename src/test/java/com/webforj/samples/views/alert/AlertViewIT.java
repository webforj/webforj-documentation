package com.webforj.samples.views.alert;

import com.webforj.component.Theme;
import com.webforj.component.button.ButtonTheme;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AlertViewIT extends BaseTest {
  private AlertPage alertPage;

  @BeforeEach
  public void setupAlertDemo() {
    alertPage = new AlertPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getAlert().assertThat().isVisible();
    alertPage.getAlert().assertThat().hasTheme(Theme.PRIMARY);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertTextIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getAlertText().assertThat().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testViewButtonIsVisible(SupportedLanguage language) {
    alertPage.setRoute(language);
    alertPage.getViewButton().assertThat().isVisible();
    alertPage.getViewButton().assertThat().hasTheme(ButtonTheme.PRIMARY);
  }

}
