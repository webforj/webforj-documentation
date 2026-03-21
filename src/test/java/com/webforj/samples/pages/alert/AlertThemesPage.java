package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Page;
import com.webforj.component.alert.Alert;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.utils.components.AlertComponent;
import com.webforj.samples.views.alert.AlertThemesView;

public class AlertThemesPage extends AbstractPage {

  public AlertThemesPage(Page page) {
    super(page, AlertThemesView.class);
  }

  public WebforjLocator getAlert() {
    return getByClass(Alert.class);
  }

  public AlertComponent getAlertComponent() {
    return new AlertComponent(getByClass(Alert.class));
  }

  public AlertComponent getAlertComponent(int index) {
    return new AlertComponent(getByClass(Alert.class).nth(index));
  }

}
