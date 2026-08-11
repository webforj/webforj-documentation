package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ElementMeterPage {
  private static final String ROUTE = "elementmeter";

  private final Locator meter;
  private final Locator caption;

  public ElementMeterPage(Page page) {
    meter = page.locator("meter");
    caption = page.getByText("7.2 GB of 10 GB used");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getMeter() {
    return meter;
  }

  public Locator getCaption() {
    return caption;
  }
}
