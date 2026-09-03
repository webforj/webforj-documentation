package com.webforj.samples.views.applayout.applayoutdrawerutility;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.applayout.applayoutdrawerutility.AppLayoutDrawerUtilityPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AppLayoutDrawerUtilityViewIT extends BaseTest {

  private AppLayoutDrawerUtilityPage appLayoutDrawerUtilityPage;

  public void setupAppLayoutDrawerUtility(SupportedLanguage language) {
    navigateToRoute(AppLayoutDrawerUtilityPage.getRoute(language));
    appLayoutDrawerUtilityPage = new AppLayoutDrawerUtilityPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testHeaderText(SupportedLanguage language) {
    setupAppLayoutDrawerUtility(language);
    assertThat(appLayoutDrawerUtilityPage.getHeaderText()).isVisible();
  }
}
