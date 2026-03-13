package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AlertExpansesPage {

  private static final String ROUTE = "alertexpanses";

  private final Locator extraSmallAlert;
  private final Locator smallAlert;
  private final Locator mediumAlert;
  private final Locator largeAlert;
  private final Locator extraLargeAlert;

  public AlertExpansesPage(Page page) {
    // Alerts are displayed in reverse order (extra-large to extra-small)
    // The page shows alerts with text "This alert uses the XXX expanse."
    this.extraSmallAlert = page.locator("dwc-alert[expanse=\"xs\"]").first();
    this.smallAlert = page.locator("dwc-alert[expanse=\"s\"]").first();
    this.mediumAlert = page.locator("dwc-alert[expanse=\"m\"]").first();
    this.largeAlert = page.locator("dwc-alert[expanse=\"l\"]").first();
    this.extraLargeAlert = page.locator("dwc-alert[expanse=\"xl\"]").first();
  }

  public static String getRoute(SupportedLanguage language) {
    return language.getPath(ROUTE);
  }

  public Locator getExtraSmallAlert() {
    return extraSmallAlert;
  }

  public Locator getSmallAlert() {
    return smallAlert;
  }

  public Locator getMediumAlert() {
    return mediumAlert;
  }

  public Locator getLargeAlert() {
    return largeAlert;
  }

  public Locator getExtraLargeAlert() {
    return extraLargeAlert;
  }
}
