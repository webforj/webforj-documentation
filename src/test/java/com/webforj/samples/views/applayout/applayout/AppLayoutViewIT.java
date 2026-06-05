package com.webforj.samples.views.applayout.applayout;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.applayout.applayout.AppLayoutPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AppLayoutViewIT extends BaseTest {
  private AppLayoutPage appLayoutPage;

  @BeforeEach
  public void setupAppLayout() {
    navigateToRoute(AppLayoutPage.getRoute());
    appLayoutPage = new AppLayoutPage(page);
  }

  public void setupAppLayout(SupportedLanguage language) {
    navigateToRoute(AppLayoutPage.getRoute(language));
    appLayoutPage = new AppLayoutPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testDashboardLink(SupportedLanguage language) {
    setupAppLayout(language);
    appLayoutPage.getDashboardLink().click();
    assertThat(page.getByText("Content for Dashboard")).isVisible();
  }
}
