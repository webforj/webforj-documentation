package com.webforj.samples.views.applayout.multipleheaders;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.applayout.multipleheaders.AppLayoutMultipleHeadersPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AppLayoutMultipleHeadersViewIT extends BaseTest {

  private AppLayoutMultipleHeadersPage appLayoutMultipleHeadersPage;

  public void setupAppLayoutMultipleHeaders(SupportedLanguage language) {
    navigateToRoute(AppLayoutMultipleHeadersPage.getRoute(language));
    appLayoutMultipleHeadersPage = new AppLayoutMultipleHeadersPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testDwcToolbar(SupportedLanguage language) {
    setupAppLayoutMultipleHeaders(language);
    assertThat(appLayoutMultipleHeadersPage.getDwcToolbar()).hasCount(2);
  }
}
