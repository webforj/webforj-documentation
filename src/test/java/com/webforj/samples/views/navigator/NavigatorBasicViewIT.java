package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.navigator.NavigatorBasicPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NavigatorBasicViewIT extends BaseTest {

  private NavigatorBasicPage navigator;

  public void setupNavigatorBasics(SupportedLanguage language) {
    navigateToRoute(NavigatorBasicPage.getRoute(language));
    navigator = new NavigatorBasicPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testRangeConsistency(SupportedLanguage language) {
    setupNavigatorBasics(language);

    navigator.clickNext();
    assertThat(navigator.navigatorValue(1)).isVisible();

    navigator.clickLast();
    assertThat(navigator.navigatorValue(10)).isVisible();

    navigator.clickPrev();
    assertThat(navigator.navigatorValue(9)).isVisible();

    navigator.clickFirst();
    assertThat(navigator.navigatorValue(0)).isVisible();

    navigator.clickPrev();
    assertThat(navigator.navigatorValue(0)).isVisible();

  }
}
