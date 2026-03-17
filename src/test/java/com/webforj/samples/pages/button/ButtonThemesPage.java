package com.webforj.samples.pages.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.button.Button;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.button.ButtonThemesView;

public class ButtonThemesPage extends AbstractPage {

  public ButtonThemesPage(Page page) {
    super(page, ButtonThemesView.class);
  }

  public WebforjLocator getSolidButton(String theme) {
    return getByClass(Button.class).getByText(theme, new Locator.GetByTextOptions().setExact(true));
  }

  public WebforjLocator getOutlinedButton(String theme) {
    return getByClass(Button.class).getByText("OUTLINED_" + theme);
  }

}
