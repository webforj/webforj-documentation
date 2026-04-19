package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.tabbedpane.TabbedPanePlacementPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TabbedPanePlacementViewIT extends BaseTest {

  private TabbedPanePlacementPage tabbedPanePlacementPage;

  public void setupPlacementTabbedPane(SupportedLanguage language) {
    navigateToRoute(TabbedPanePlacementPage.getRoute(language));
    tabbedPanePlacementPage = new TabbedPanePlacementPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testPlacementTabbedPane(SupportedLanguage language) {
    setupPlacementTabbedPane(language);
    tabbedPanePlacementPage.getPlacementDropdown().click();
    tabbedPanePlacementPage.getAlignmentDropdownButton().click(); //BOTTOM
    assertThat(tabbedPanePlacementPage.getPlacementTabbedPane()).hasAttribute("placement", "bottom");

  }
}
