package com.webforj.samples.pages.navigator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class NavigatorPagesPage {

  private static final String ROUTE = "navigatorpages";

  private final Page page;

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

  public static String getRoute() {
    return ROUTE;
  }

    public Locator showingRange(int from, int to) {
        String regex = String.format("^\\s*Showing %d to %d of 100\\s*$", from, to);
        return page.getByText(Pattern.compile(regex));
    }
}
