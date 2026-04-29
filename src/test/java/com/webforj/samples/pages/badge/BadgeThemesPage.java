package com.webforj.samples.pages.badge;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BadgeThemesPage {

  private static final String ROUTE = "badgethemes";

  private final Locator primaryBadge;
  private final Locator successBadge;
  private final Locator dangerBadge;
  private final Locator outlinedPrimaryBadge;
  private final Locator outlinedSuccessBadge;

  public BadgeThemesPage(Page page) {
    this.primaryBadge =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("Primary")).first();
    this.successBadge =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("Success")).first();
    this.dangerBadge =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("Danger")).first();
    this.outlinedPrimaryBadge =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("Primary")).last();
    this.outlinedSuccessBadge =
        page.locator("dwc-badge").filter(new Locator.FilterOptions().setHasText("Success")).last();
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getPrimaryBadge() {
    return primaryBadge;
  }

  public Locator getSuccessBadge() {
    return successBadge;
  }

  public Locator getDangerBadge() {
    return dangerBadge;
  }

  public Locator getOutlinedPrimaryBadge() {
    return outlinedPrimaryBadge;
  }

  public Locator getOutlinedSuccessBadge() {
    return outlinedSuccessBadge;
  }
}
