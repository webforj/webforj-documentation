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

  /**
   * Selects an option from the ChoiceBox by clicking on it and then selecting the option.
   * Since dwc-choicebox is a web component, we need to interact with it via clicks
   * rather than using the native selectOption method.
   *
   * @param optionText the visible text of the option to select
   */
  public void selectExpanse(String optionText) {
    // Click on the ChoiceBox to open the dropdown
    getChoiceBox().click();

    // Wait for the dropdown to appear and click on the desired option
    // The option text is prefixed with the value (e.g., "LARGE" shows as "LARGE" in the list)
    getByText(optionText).nth(2).click();
  }

}
