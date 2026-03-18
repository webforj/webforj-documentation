package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Page;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.checkbox.CheckboxExpanseView;

public class CheckboxExpansePage extends AbstractPage {

  public CheckboxExpansePage(Page page) {
    super(page, CheckboxExpanseView.class);
  }

  public WebforjLocator getCheckbox(int index) {
    return getByClass(CheckBox.class).nth(index);
  }

}
