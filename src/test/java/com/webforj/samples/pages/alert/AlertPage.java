package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Page;
import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.alert.AlertView;

public class AlertPage extends AbstractPage {

  public AlertPage(Page page) {
    super(page, AlertView.class);
  }

  public WebforjLocator getAlert() {
    return getByClass(Alert.class);
  }

  public WebforjLocator getAlertText() {
    return getByText("The requested information is ready to be viewed.");
  }

  public WebforjLocator getViewButton() {
    return getByClass(Button.class);
  }

}
