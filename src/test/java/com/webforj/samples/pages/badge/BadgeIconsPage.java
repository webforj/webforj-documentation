package com.webforj.samples.pages.badge;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BadgeIconsPage {

  private static final String ROUTE = "badgeicons";

  private final Locator doneBadge;
  private final Locator errorBadge;
  private final Locator allBadges;

  public BadgeIconsPage(Page page) {
    this.doneBadge =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("Done"));
    this.errorBadge =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("Error"));
    this.allBadges = page.locator("dwc-badge");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getDoneBadge() {
    return doneBadge;
  }

  public Locator getErrorBadge() {
    return errorBadge;
  }

  public Locator getAllBadges() {
    return allBadges;
  }
}
