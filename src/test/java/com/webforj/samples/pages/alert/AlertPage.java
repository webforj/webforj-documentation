package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.SupportedLanguage;

public class AlertPage {

  private static final String ROUTE = "alert";

  private final Locator alert;
  private final Locator alertText;
  private final Locator viewButton;

  public AlertPage(Page page) {
    this.alert = page.locator("dwc-alert").first();
    this.alertText = page.getByText("The requested information is ready to be viewed.");
    this.viewButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View"));
  }

  public static String getRoute(SupportedLanguage language) {
    return language.getPath(ROUTE);
  }

  public Locator getAlert() {
    return alert;
  }

  public Locator getAlertText() {
    return alertText;
  }

  public Locator getViewButton() {
    return viewButton;
  }
}
