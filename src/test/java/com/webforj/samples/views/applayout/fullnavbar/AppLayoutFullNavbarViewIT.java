package com.webforj.samples.views.applayout.fullnavbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.applayout.fullnavbar.AppLayoutFullNavbarPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppLayoutFullNavbarViewIT extends BaseTest {

  private AppLayoutFullNavbarPage appLayoutFullNavbarPage;

  @BeforeEach
  public void setupAppLayoutFullNavbar() {
    navigateToRoute(AppLayoutFullNavbarPage.getRoute());
    appLayoutFullNavbarPage = new AppLayoutFullNavbarPage(page);
  }

  @Test
  public void testHeaderOffScreen() {
    assertThat(appLayoutFullNavbarPage.getHeaderText()).isEmpty();
  }
}
