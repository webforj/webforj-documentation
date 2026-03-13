package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.utils.NodeNameUtils;
import com.webforj.samples.utils.ThemeUtils;

public class AlertPage {
  private static final String ROUTE = "alert";

  private final Locator alert;
  private final Locator alertText;
  private final Locator viewButton;

  public AlertPage(Page page) {
    this.alert = page.locator(NodeNameUtils.getNodeName(Alert.class)).first();
    this.alertText = page.getByText("The requested information is ready to be viewed.");
    this.viewButton = ThemeUtils.getLocator(page, NodeNameUtils.getNodeName(Button.class), Theme.PRIMARY, new Page.LocatorOptions().setHasText("View"));
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
