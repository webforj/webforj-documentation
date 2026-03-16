package com.webforj.samples.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.impl.LocatorAssertionsImpl;
import com.webforj.component.Expanse;
import com.webforj.component.Theme;
import com.webforj.component.button.ButtonTheme;

import java.util.EnumMap;
import java.util.Map;

/**
 * A delegating {@link LocatorAssertions} implementation that wraps Playwright's assertions to provide
 * webforj-specific enhancements.
 *
 * <p>This class serves as an extension point for adding custom assertions tailored for webforj components.
 */
public class WebforjAssertions extends LocatorAssertionsImpl implements LocatorAssertions {
  private static final Map<Expanse, String> EXPANSE_VALUES;
  private final LocatorAssertions assertions;
  private final Locator locator;

  static {
    EXPANSE_VALUES = new EnumMap<>(Expanse.class);
    for (Expanse expanse: Expanse.values()) {
      switch (expanse) {
        case XLARGE, XSMALL -> EXPANSE_VALUES.put(expanse, expanse.name().toLowerCase().substring(0, 2));
        case NONE -> EXPANSE_VALUES.put(Expanse.NONE, "");
        default -> EXPANSE_VALUES.put(expanse, expanse.name().toLowerCase().substring(0, 1));
      }
    }
  }

  public WebforjAssertions(LocatorAssertions assertions, Locator locator) {
    super(locator);
    this.assertions = assertions;
    this.locator = locator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WebforjAssertions not() {
    return new WebforjAssertions(assertions.not(), locator);
  }

  public void hasTheme(Theme theme) {
    assertions.hasAttribute("theme", theme.name().toLowerCase());
  }

  public void hasTheme(ButtonTheme theme) {
    assertions.hasAttribute("theme", theme.name().toLowerCase().replace("_", "-"));
  }

  public void hasExpanse(Expanse expanse) {
    assertions.hasAttribute("expanse", EXPANSE_VALUES.get(expanse));
  }

  public void isVisibleAfterClick(Locator clickTarget) {
    assertions.not().isVisible();
    clickTarget.click();
    assertions.isVisible();
  }

}
