package com.webforj.samples.pages.button;

import com.microsoft.playwright.Page;
import com.webforj.component.button.Button;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.button.ButtonExpansesView;

public class ButtonExpansesPage extends AbstractPage {

  public ButtonExpansesPage(Page page) {
    super(page, ButtonExpansesView.class);
  }

  public WebforjLocator getButton() {
    return getByText("None");
  }

  public WebforjLocator getChoiceBox() {
    return getByClass(ChoiceBox.class);
  }

}
