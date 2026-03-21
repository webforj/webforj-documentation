package com.webforj.samples.pages.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.button.Button;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.ButtonComponent;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.button.ButtonThemesView;

public class ButtonThemesPage extends AbstractPage {

  public ButtonThemesPage(Page page) {
    super(page, ButtonThemesView.class);
  }

  public ButtonComponent getSolidButton(String theme) {
    return new ButtonComponent(getByClass(Button.class).getByText(theme,
        new Locator.GetByTextOptions().setExact(true)));
  }

  public ButtonComponent getOutlinedButton(String theme) {
    return new ButtonComponent(getByClass(Button.class).getByText("OUTLINED_" + theme));
  }

}
