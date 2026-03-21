package com.webforj.samples.pages.button;

import com.microsoft.playwright.Page;
import com.webforj.component.button.Button;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.ButtonComponent;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.button.ButtonIconView;

import java.util.List;

public class ButtonIconPage extends AbstractPage {

  public ButtonIconPage(Page page) {
    super(page, ButtonIconView.class);
  }

  public ButtonComponent getButtons(int index) {
    return new ButtonComponent(getByClass(Button.class).nth(index));
  }

  public List<String> getTexts() {
    return List.of("Notifications", "Search", "");
  }

}
