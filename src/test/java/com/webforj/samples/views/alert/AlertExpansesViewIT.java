package com.webforj.samples.views.alert;

import com.webforj.component.Expanse;
import com.webforj.component.Theme;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertExpansesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AlertExpansesViewIT extends BaseTest {
  private AlertExpansesPage alertPage;

  @BeforeEach
  public void setupAlertExpansesDemo() {
    alertPage = new AlertExpansesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertHasCorrectExpanse(SupportedLanguage language) {
    alertPage.setRoute(language);
    Expanse[] expanses = Expanse.values();
    for (int i = expanses.length - 1, j = 0; i >= 0; i--, j++) {
      alertPage.getAlertComponent(i).assertIsVisible();
      alertPage.getAlertComponent(i).assertTheme(Theme.SUCCESS);
      alertPage.getAlertComponent(i).assertExpanse(expanses[j]);
    }
  }

}
