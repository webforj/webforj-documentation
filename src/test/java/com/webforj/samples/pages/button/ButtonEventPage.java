package com.webforj.samples.pages.button;

import com.microsoft.playwright.Page;
import com.webforj.component.button.Button;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.ButtonComponent;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.button.ButtonEventView;

public class ButtonEventPage extends AbstractPage {

  public ButtonEventPage(Page page) {
    super(page, ButtonEventView.class);
  }

  public ButtonComponent getButton() {
    return new ButtonComponent(getByClass(Button.class).getByText("Click Me!"));
  }

  public WebforjLocator getCounterText(String text) {
    return getByText("Current Counter: " + text);
  }

}
