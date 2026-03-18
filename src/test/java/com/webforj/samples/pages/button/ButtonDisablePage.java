package com.webforj.samples.pages.button;

import com.microsoft.playwright.Page;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.button.ButtonDisableView;

public class ButtonDisablePage extends AbstractPage {

  public ButtonDisablePage(Page page) {
    super(page, ButtonDisableView.class);
  }

  public WebforjLocator getSubmitButton() {
    return getButton("Submit");
  }

  public WebforjLocator getEmailInput() {
    return getByClass(TextField.class).locator("input");
  }

}
