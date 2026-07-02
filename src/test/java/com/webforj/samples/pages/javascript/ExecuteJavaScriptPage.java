package com.webforj.samples.pages.javascript;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ExecuteJavaScriptPage {
  private static final String ROUTE = "executejavascript";

  private final Locator copyButton;
  private final Locator copiedToast;

  public ExecuteJavaScriptPage(Page page) {
    copyButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Copy link"));
    copiedToast = page.getByText("Link copied");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getCopyButton() {
    return copyButton;
  }

  public Locator getCopiedToast() {
    return copiedToast;
  }
}
