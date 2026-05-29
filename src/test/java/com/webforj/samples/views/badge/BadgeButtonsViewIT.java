package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.badge.BadgeButtonsPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BadgeButtonsViewIT extends BaseTest {

  private BadgeButtonsPage badgeButtonsPage;

  @BeforeEach
  public void setupBadgeButtons() {
    navigateToRoute(BadgeButtonsPage.getRoute());
    badgeButtonsPage = new BadgeButtonsPage(page);
  }

  @Test
  public void testNotificationsButtonIsVisible() {
    assertThat(badgeButtonsPage.getNotificationsButton()).isVisible();
  }

  @Test
  public void testNotificationsButtonHasBadge() {
    assertThat(badgeButtonsPage.getNotificationsButton().locator("dwc-badge")).isVisible();
  }

  @Test
  public void testNotificationsButtonBadgeShowsCount() {
    assertThat(badgeButtonsPage.getNotificationsButton().locator("dwc-badge")).hasText("5");
  }

  @Test
  public void testMessagesButtonBadgeShowsCount() {
    assertThat(badgeButtonsPage.getMessagesButton().locator("dwc-badge")).hasText("12");
  }
}
