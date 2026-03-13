package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.Expanse;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.alert.AlertExpansesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AlertExpansesViewIT extends BaseTest {

  private AlertExpansesPage alertPage;

  public void setupAlertExpansesDemo(SupportedLanguage language) {
    navigateToRoute(AlertExpansesPage.getRoute(language));
    alertPage = new AlertExpansesPage(page);
  }

  static Stream<Arguments> provideLanguageAndExpanse() {
    List<Arguments> arguments = new ArrayList<>();
    for (SupportedLanguage language : SupportedLanguage.values()) {
      for (Expanse expanse : Expanse.values()) {
        arguments.add(Arguments.of(language, expanse));
      }
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideLanguageAndExpanse")
  public void testAlertExpanseIsVisible(SupportedLanguage language, Expanse expanse) {
    setupAlertExpansesDemo(language);

    switch (expanse) {
      case XSMALL:
        assertThat(alertPage.getExtraSmallAlert()).isVisible();
        break;
      case SMALL:
        assertThat(alertPage.getSmallAlert()).isVisible();
        break;
      case MEDIUM:
        assertThat(alertPage.getMediumAlert()).isVisible();
        break;
      case LARGE:
        assertThat(alertPage.getLargeAlert()).isVisible();
        break;
      case XLARGE:
        assertThat(alertPage.getExtraLargeAlert()).isVisible();
        break;
    }
  }

  @ParameterizedTest
  @MethodSource("provideLanguageAndExpanse")
  public void testAlertHasCorrectExpanse(SupportedLanguage language, Expanse expanse) {
    setupAlertExpansesDemo(language);

    switch (expanse) {
      case XSMALL:
        assertThat(alertPage.getExtraSmallAlert()).hasAttribute("expanse", "xs");
        break;
      case SMALL:
        assertThat(alertPage.getSmallAlert()).hasAttribute("expanse", "s");
        break;
      case MEDIUM:
        assertThat(alertPage.getMediumAlert()).hasAttribute("expanse", "m");
        break;
      case LARGE:
        assertThat(alertPage.getLargeAlert()).hasAttribute("expanse", "l");
        break;
      case XLARGE:
        assertThat(alertPage.getExtraLargeAlert()).hasAttribute("expanse", "xl");
        break;
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAlertsHaveSuccessTheme(SupportedLanguage language) {
    setupAlertExpansesDemo(language);
    assertThat(alertPage.getExtraSmallAlert()).hasAttribute("theme", "success");
    assertThat(alertPage.getSmallAlert()).hasAttribute("theme", "success");
    assertThat(alertPage.getMediumAlert()).hasAttribute("theme", "success");
    assertThat(alertPage.getLargeAlert()).hasAttribute("theme", "success");
    assertThat(alertPage.getExtraLargeAlert()).hasAttribute("theme", "success");
  }
}
