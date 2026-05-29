package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.tabbedpane.TabbedPaneBorderPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TabbedPaneBorderViewIT extends BaseTest {

  private TabbedPaneBorderPage tabbedPaneBorderPage;

  public void setupBorderTabbedPane(SupportedLanguage language) {
    navigateToRoute(TabbedPaneBorderPage.getRoute(language));
    tabbedPaneBorderPage = new TabbedPaneBorderPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testBorderTabbedPane(SupportedLanguage language) {
    setupBorderTabbedPane(language);
    tabbedPaneBorderPage.getHideBorderToggle().click();
    assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("borderless", "");

    tabbedPaneBorderPage.getHideActiveIndicatorToggle().click();
    assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("hide-active-indicator", "");

    tabbedPaneBorderPage.getOrdersTab().click();
    assertThat(tabbedPaneBorderPage.getOrdersTab()).hasAttribute("aria-selected", "true");
  }
}
