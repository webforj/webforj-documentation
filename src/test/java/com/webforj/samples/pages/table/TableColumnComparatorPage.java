package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.SupportedLanguage;

public class TableColumnComparatorPage {
  private static final String ROUTE = "tablecolumncomparator";

  private final Locator numberColumnHeader;
  private final Locator numberCells;

  public TableColumnComparatorPage(Page page) {

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getNumberColumnHeader() {
    return numberColumnHeader;
  }

  public Locator getNumberCells() {
    return numberCells;
  }
}
