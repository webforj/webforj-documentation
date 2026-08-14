package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ElementFigurePage {
  private static final String ROUTE = "elementfigure";

  private final Locator quote;
  private final Locator caption;

  public ElementFigurePage(Page page) {
    quote = page.locator("blockquote");
    caption = page.locator("figcaption");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getQuote() {
    return quote;
  }

  public Locator getCaption() {
    return caption;
  }
}
