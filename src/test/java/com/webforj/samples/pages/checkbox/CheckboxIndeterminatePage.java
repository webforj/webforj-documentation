package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Page;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.components.CheckboxComponent;
import com.webforj.samples.views.checkbox.CheckboxIndeterminateView;

public class CheckboxIndeterminatePage extends AbstractPage {

  public CheckboxIndeterminatePage(Page page) {
    super(page, CheckboxIndeterminateView.class);
  }

  public CheckboxComponent getParentCheckbox() {
    return new CheckboxComponent(getByClass(CheckBox.class).nth(0));
  }

  public CheckboxComponent getChild1Checkbox() {
    return new CheckboxComponent(getByClass(CheckBox.class).nth(1));
  }

  public CheckboxComponent getChild2Checkbox() {
    return new CheckboxComponent(getByClass(CheckBox.class).nth(2));
  }

}
