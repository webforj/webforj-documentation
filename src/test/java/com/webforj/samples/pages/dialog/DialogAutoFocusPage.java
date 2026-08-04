package com.webforj.samples.pages.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DialogAutoFocusPage {

  private static final String ROUTE = "dialogautofocus";

  private final Locator openDialog;
  private final Locator textField;

  public DialogAutoFocusPage(Page page) {
    this.openDialog =
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Invite teammate"));
    this.textField =
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address"));
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getOpenDialog() {
    return openDialog;
  }

  public Locator getTextField() {
    return textField;
  }
}
