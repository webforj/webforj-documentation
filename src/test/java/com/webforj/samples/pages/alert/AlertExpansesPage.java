package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.Expanse;
import com.webforj.component.alert.Alert;
import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.utils.ExpanseUtils;
import com.webforj.samples.utils.NodeNameUtils;

public class AlertExpansesPage {
  private static final String ROUTE = "alertexpanses";

  private final Page page;

  public AlertExpansesPage(Page page) {
    this.page = page;
  }

  public static String getRoute(SupportedLanguage language) {
    return language.getPath(ROUTE);
  }

  public Locator getAlert(Expanse expanse) {
    return ExpanseUtils.getLocator(page, NodeNameUtils.getNodeName(Alert.class), expanse);
  }

}
