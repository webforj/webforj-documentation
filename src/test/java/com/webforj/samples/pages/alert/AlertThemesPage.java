package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.utils.NodeNameUtils;
import com.webforj.samples.utils.ThemeUtils;

public class AlertThemesPage {
  private static final String ROUTE = "alertthemes";

  private final Page page;

  public AlertThemesPage(Page page) {
    this.page = page;
  }

  public static String getRoute(SupportedLanguage language) {
    return language.getPath(ROUTE);
  }

  public Locator getAlert(Theme theme) {
    return ThemeUtils.getLocator(page, NodeNameUtils.getNodeName(Alert.class), theme);
  }

}
