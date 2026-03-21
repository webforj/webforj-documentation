package com.webforj.samples.pages.button;

import com.microsoft.playwright.Page;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.ButtonComponent;
import com.webforj.samples.utils.components.ChoiceBoxComponent;
import com.webforj.samples.views.button.ButtonExpansesView;

public class ButtonExpansesPage extends AbstractPage {

  public ButtonExpansesPage(Page page) {
    super(page, ButtonExpansesView.class);
  }

  public ButtonComponent getButton(String text) {
    return new ButtonComponent(getByText(text));
  }

  public ChoiceBoxComponent getChoiceBox() {
    return new ChoiceBoxComponent(getByClass(ChoiceBox.class));
  }

  /**
   * Selects an option from the ChoiceBox by its visible text.
   *
   * @param optionText the visible text of the option to select
   */
  public void selectExpanse(String optionText) {
    getChoiceBox().selectByText(optionText);
  }

}
