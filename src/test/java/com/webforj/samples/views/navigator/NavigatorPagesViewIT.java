package com.webforj.samples.views.navigator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.navigator.NavigatorPagesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NavigatorPagesViewIT extends BaseTest {

  private NavigatorPagesPage navigator;

  public void setupNavigatorPage(SupportedLanguage language) {
    navigateToRoute(NavigatorPagesPage.getRoute(language));
    navigator = new NavigatorPagesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testVerifyEllipsis(SupportedLanguage language) {
    setupNavigatorPage(language);
    navigator.navigatorValue(4).click();

    assertThat(navigator.showingRange(31, 40)).isVisible();
  }
}
