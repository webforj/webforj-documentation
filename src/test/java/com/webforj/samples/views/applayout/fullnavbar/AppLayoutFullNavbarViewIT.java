package com.webforj.samples.views.applayout.fullnavbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.applayout.fullnavbar.AppLayoutFullNavbarPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AppLayoutFullNavbarViewIT extends BaseTest {

  private AppLayoutFullNavbarPage appLayoutFullNavbarPage;

  public void setupAppLayoutFullNavbar(SupportedLanguage language) {
    navigateToRoute(AppLayoutFullNavbarPage.getRoute(language));
    appLayoutFullNavbarPage = new AppLayoutFullNavbarPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testHeaderOffScreen(SupportedLanguage language) {
    setupAppLayoutFullNavbar(language);
    assertThat(appLayoutFullNavbarPage.getHeaderText()).isEmpty();
  }
}
