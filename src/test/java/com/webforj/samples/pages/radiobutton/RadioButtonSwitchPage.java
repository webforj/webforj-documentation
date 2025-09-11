package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class RadioButtonSwitchPage {

  private static final String ROUTE = "radiobuttonswitch";

  private final Locator switchRB;

  public RadioButtonSwitchPage(Page page) {

    this.switchRB = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Switch RadioButton"));

  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getSwitchRadio() {
    return switchRB;
  }
}