package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ElementSearchPage {
  private static final String ROUTE = "elementsearch";

  private final Locator searchInput;
  private final Locator focusButton;

  public ElementSearchPage(Page page) {
    searchInput = page.getByPlaceholder("Search products");
    focusButton =
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Focus search"));
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getSearchInput() {
    return searchInput;
  }

  public Locator getFocusButton() {
    return focusButton;
  }
}
