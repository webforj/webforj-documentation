package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.checkbox.CheckboxIndeterminateView;

public class CheckboxIndeterminatePage extends AbstractPage {

  public CheckboxIndeterminatePage(Page page) {
    super(page, CheckboxIndeterminateView.class);
  }

  public Locator getParentCheckbox() {
    return page.getByText("Parent");
  }

  public Locator getChild1Checkbox() {
    return page.getByText("Child 1");
  }

  public Locator getChild2Checkbox() {
    return page.getByText("Child 2");
  }

}
