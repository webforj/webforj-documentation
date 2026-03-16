package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Page;
import com.webforj.component.alert.Alert;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.alert.AlertExpansesView;

public class AlertExpansesPage extends AbstractPage {

  public AlertExpansesPage(Page page) {
    super(page, AlertExpansesView.class);
  }

  public WebforjLocator getAlert() {
    return getByClass(Alert.class);
  }

}
