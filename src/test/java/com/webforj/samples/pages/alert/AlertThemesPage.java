package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AlertThemesPage {

  private static final String ROUTE = "alertthemes";

  private final Locator defaultAlert;
  private final Locator primaryAlert;
  private final Locator successAlert;
  private final Locator warningAlert;
  private final Locator dangerAlert;
  private final Locator infoAlert;

  public AlertThemesPage(Page page) {
    this.defaultAlert = page.locator("dwc-alert[theme='default']").first();
    this.primaryAlert = page.locator("dwc-alert[theme='primary']").first();
    this.successAlert = page.locator("dwc-alert[theme='success']").first();
    this.warningAlert = page.locator("dwc-alert[theme='warning']").first();
    this.dangerAlert = page.locator("dwc-alert[theme='danger']").first();
    this.infoAlert = page.locator("dwc-alert[theme='info']").first();
  }

  public static String getRoute(SupportedLanguage language) {
    return language.getPath(ROUTE);
  }

  public Locator getDefaultAlert() {
    return defaultAlert;
  }

  public Locator getPrimaryAlert() {
    return primaryAlert;
  }

  public Locator getSuccessAlert() {
    return successAlert;
  }

  public Locator getWarningAlert() {
    return warningAlert;
  }

  public Locator getDangerAlert() {
    return dangerAlert;
  }

  public Locator getInfoAlert() {
    return infoAlert;
  }
}
