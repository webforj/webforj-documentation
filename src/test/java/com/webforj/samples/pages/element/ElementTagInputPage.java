package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ElementTagInputPage {
  private static final String ROUTE = "elementtaginput";

  private final Page page;
  private final Locator inputField;

  public ElementTagInputPage(Page page) {
    this.page = page;
    inputField = page.getByPlaceholder("Type a tag and press Enter");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getInputField() {
    return inputField;
  }

  public Locator getTag(String text) {
    return page.getByText(text);
  }
}
