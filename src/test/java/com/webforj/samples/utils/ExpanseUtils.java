package com.webforj.samples.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.Expanse;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class ExpanseUtils {
  private static final Map<Expanse, String> expanseMap = Map.of(
    Expanse.XLARGE, "xl",
    Expanse.LARGE, "l",
    Expanse.MEDIUM, "m",
    Expanse.SMALL, "s",
    Expanse.XSMALL, "xs",
    Expanse.NONE, ""
  );

  public static Locator getLocator(Page page, String prefix, Expanse expanse) {
    String locator = String.format("%s[expanse='%s']", prefix, expanseMap.get(expanse));
    return page.locator(locator).first();
  }

  public static Locator getLocator(Page page, String prefix, Expanse expanse, Page.LocatorOptions options) {
    String locator = String.format("%s[expanse='%s']", prefix, expanseMap.get(expanse));
    return page.locator(locator, options).first();
  }

  public static String getAttributeValue(Expanse expanse) {
    return expanseMap.get(expanse);
  }

  public static void assertExpanse(Locator locator, Expanse expanse) {
    assertThat(locator).hasAttribute("expanse", expanseMap.get(expanse));
  }

  public static void assertForEach(Consumer<? super Expanse> action) {
    for (Expanse expanse: Expanse.values()) {
      action.accept(expanse);
    }
  }

}
