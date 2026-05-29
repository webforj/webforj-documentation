package com.webforj.samples.pages.badge;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BadgeSizesPage {

  private static final String ROUTE = "badgesizes";

  private final Locator allSizeBadges;
  private final Locator allCircularBadges;

  public BadgeSizesPage(Page page) {
    // The view has two FlexLayouts: first contains the size badges, second the circular ones.
    // All badges on the page are dwc-badge elements.
    this.allSizeBadges = page.locator("dwc-badge");

    // Circular badges are in the second group and all have text "5"
    this.allCircularBadges =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("5"));
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getAllSizeBadges() {
    return allSizeBadges;
  }

  public Locator getSizeBadgeByIndex(int index) {
    return allSizeBadges.nth(index);
  }

  public Locator getAllCircularBadges() {
    return allCircularBadges;
  }
}
