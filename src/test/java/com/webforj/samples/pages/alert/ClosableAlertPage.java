package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.component.alert.Alert;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.utils.NodeNameUtils;

public class ClosableAlertPage {

  private static final String ROUTE = "closablealert";

  private final Locator alert;
  private final Locator alertText;
  private final Locator closeButton;
  private final Locator showAlertButton;

  public ClosableAlertPage(Page page) {
    this.alert = page.locator(NodeNameUtils.getNodeName(Alert.class)).first();
    this.alertText = page.getByText("Heads up! This alert can be dismissed.");
    this.closeButton = page.locator("dwc-alert button").first();
    this.showAlertButton = page.getByRole(AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Show Alert"));
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

  public Locator getCloseButton() {
    return closeButton;
  }

  public Locator getShowAlertButton() {
    return showAlertButton;
  }
}
