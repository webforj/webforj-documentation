package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.tabbedpane.TabbedPaneBorderPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TabbedPaneBorderViewIT extends BaseTest {

  private TabbedPaneBorderPage tabbedPaneBorderPage;

  @BeforeEach
  public void setupBorderTabbedPane() {
    navigateToRoute(TabbedPaneBorderPage.getRoute());
    tabbedPaneBorderPage = new TabbedPaneBorderPage(page);
  }

  @Test
  public void testBorderTabbedPane() {
    tabbedPaneBorderPage.getHideBorderToggle().click();
    assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("borderless", "");

    tabbedPaneBorderPage.getHideActiveIndicatorToggle().click();
    assertThat(tabbedPaneBorderPage.getBorderTabbedPane())
        .hasAttribute("hide-active-indicator", "");

    tabbedPaneBorderPage.getOrdersTab().click();
    assertThat(tabbedPaneBorderPage.getOrdersTab()).hasAttribute("aria-selected", "true");
  }
}
