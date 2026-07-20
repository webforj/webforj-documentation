package com.webforj.samples.pages.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DialogSectionsPage {

  private static final String ROUTE = "dialogsections";

  private final Locator header;
  private final Locator content;
  private final Locator footer;

  public DialogSectionsPage(Page page) {
    this.header = page.locator("dwc-dialog > [slot='header']");
    this.content = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Name"));
    this.footer =
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save changes"));
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getHeader() {
    return header;
  }

  public Locator getContent() {
    return content;
  }

  public Locator getFooter() {
    return footer;
  }
}
