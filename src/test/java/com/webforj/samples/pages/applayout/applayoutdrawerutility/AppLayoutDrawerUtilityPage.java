package com.webforj.samples.pages.applayout.applayoutdrawerutility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AppLayoutDrawerUtilityPage {

  private static final String ROUTE = "applayoutdrawerutility";

  private final Locator headerText;

  public AppLayoutDrawerUtilityPage(Page page) {

    this.headerText = page.getByText("Joe Smith");
  }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getHeaderText() {
        return headerText;
    }

  public Locator getHeaderText() {
    return headerText;
  }
}
