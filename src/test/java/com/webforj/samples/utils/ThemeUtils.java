package com.webforj.samples.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.Theme;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class ThemeUtils {

  public static Locator getLocator(Page page, String prefix, Theme theme) {
    String locator = String.format("%s[theme='%s']", prefix, getAttributeValue(theme));
    return page.locator(locator).first();
  }

  public static Locator getLocator(Page page, String prefix, Theme theme, Page.LocatorOptions options) {
    String locator = String.format("%s[theme='%s']", prefix, getAttributeValue(theme));
    return page.locator(locator, options).first();
  }

  public static String getAttributeValue(Theme theme) {
    return theme.name().toLowerCase();
  }

  public static void assertTheme(Locator locator, Theme theme) {
    assertThat(locator).hasAttribute("theme", getAttributeValue(theme));
  }

  public static void assertForEach(Consumer<? super Theme> action) {
    for (Theme theme: Theme.values()) {
      action.accept(theme);
    }
  }
}
