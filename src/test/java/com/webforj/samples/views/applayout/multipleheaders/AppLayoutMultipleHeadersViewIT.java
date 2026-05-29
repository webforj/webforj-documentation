package com.webforj.samples.views.applayout.multipleheaders;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.applayout.multipleheaders.AppLayoutMultipleHeadersPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppLayoutMultipleHeadersViewIT extends BaseTest {

  private AppLayoutMultipleHeadersPage appLayoutMultipleHeadersPage;

  @BeforeEach
  public void setupAppLayoutMultipleHeaders() {
    navigateToRoute(AppLayoutMultipleHeadersPage.getRoute());
    appLayoutMultipleHeadersPage = new AppLayoutMultipleHeadersPage(page);
  }

  @Test
  public void testDwcToolbar() {
    assertThat(appLayoutMultipleHeadersPage.getDwcToolbar()).hasCount(2);
  }
}
