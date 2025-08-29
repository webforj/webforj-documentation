package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class RadioButtonSwitchPage extends BasePage {

  private static final String ROUTE = "radiobuttonswitch";

  private final Locator switchRB;

  public RadioButtonSwitchPage(Page page) {
    super(page);

    this.switchRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Switch RadioButton").setExact(true));

  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getSwitchRadio() {
    return switchRB;
  }
}