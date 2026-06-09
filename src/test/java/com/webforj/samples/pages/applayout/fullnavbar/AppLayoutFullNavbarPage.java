package com.webforj.samples.pages.applayout.fullnavbar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AppLayoutFullNavbarPage {

  private static final String ROUTE = "applayoutfullnavbar";

  private final Locator header;

  public AppLayoutFullNavbarPage(Page page) {
    this.header = page.locator("header");
  }

  public static String getRoute(SupportedLanguage language) {
    return language.getPath(ROUTE);
  }

  public Locator getHeaderText() {
    return header;
  }
}
