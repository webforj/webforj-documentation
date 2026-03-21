package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.alert.Alert;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.utils.components.AlertComponent;
import com.webforj.samples.views.alert.ClosableAlertView;

public class ClosableAlertPage extends AbstractPage {

  public ClosableAlertPage(Page page) {
    super(page, ClosableAlertView.class);
  }

  public WebforjLocator getAlert() {
    return getByClass(Alert.class);
  }

  public AlertComponent getAlertComponent() {
    return new AlertComponent(getByClass(Alert.class));
  }

  public WebforjLocator getAlertText() {
    return getByText("Heads up! This alert can be dismissed.");
  }

  public WebforjLocator getCloseButton() {
    return locator("dwc-alert button");
  }

  // Unsure why, but using a WebforjLocator does not work in this case
  public Locator getShowAlertButton() {
    return page.locator("dwc-button");
  }
}
