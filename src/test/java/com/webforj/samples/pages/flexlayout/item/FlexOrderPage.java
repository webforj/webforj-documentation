package com.webforj.samples.pages.flexlayout.item;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.Pattern;

public class FlexOrderPage {

  private static final String ROUTE = "flexorder";

  private final Page page;
  private final Locator setOrderButton;

  public FlexOrderPage(Page page) {
    this.page = page;
    this.setOrderButton =
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Set Order"));
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getSetOrderButton() {
    return setOrderButton;
  }

  public Locator buttonValue(int n) {
    Pattern exact = Pattern.compile("^\\s*Order:\\s*" + n + "\\s*$");
    return page.getByText(exact);
  }
}
