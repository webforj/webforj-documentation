package com.webforj.samples.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.impl.LocatorAssertionsImpl;
import com.webforj.component.Expanse;
import com.webforj.component.ExpanseBase;
import com.webforj.component.Theme;
import com.webforj.component.ThemeBase;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.component.button.ButtonTheme;
import com.webforj.concern.HasHorizontalAlignment;

import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

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
  private boolean isNegated = false;

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
    this.isNegated = false;
  }

  private WebforjAssertions(WebforjAssertions other, boolean negated) {
    super(other.locator);
    this.assertions = other.assertions;
    this.locator = other.locator;
    this.isNegated = negated;
  }

  private static String getExpanseValue(String expanse) {
    String value = expanse.toLowerCase();
    if (value.equals("none")) {
      return "";
    }
    int xCount = 0;
    for (int i = 0; i < value.length() && value.charAt(i) == 'x'; i++) {
      xCount++;
    }
    if (xCount == 0) {
      return value.substring(0, 1);
    } else if (xCount == 1) {
      return value.substring(0, 2);
    } else {
      return xCount + "x" + value.charAt(xCount);
    }
  }

  public static String getExpanseValue(ExpanseBase expanse) {
    return getExpanseValue(expanse.toString());
  }

  public static String getThemeValue(ThemeBase theme) {
    return theme.toString().toLowerCase().replace("_", "-");
  }

  public static void hasHorizontalAlignment(LocatorAssertions assertions, HasHorizontalAlignment.Alignment alignment) {
    switch (alignment) {
      case LEFT -> assertions.hasClass(Pattern.compile("(^|\\s)bbj-reverse-order(\\s|$)"));
      case MIDDLE -> {} // ?
      case RIGHT -> assertions.not().hasClass(Pattern.compile("(^|\\s)bbj-reverse-order(\\s|$)"));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WebforjAssertions not() {
    return new WebforjAssertions(assertions.not(), locator);
  }

  private void hasTheme(String theme) {
    String value = theme.toLowerCase().replace("_", "-");
    assertions.hasAttribute("theme", value);
  }

  public void isDisabled() {
    assertions.hasAttribute("disabled", "");
  }

  public void hasTheme(ThemeBase theme) {
    hasTheme(theme.toString());
  }

  public void hasTheme(Theme theme) {
    hasTheme(theme.name());
  }

  public void hasTheme(AvatarTheme theme) {
    hasTheme(theme.name());
  }

  public void hasTheme(ButtonTheme theme) {
    hasTheme(theme.name());
  }

  public void hasExpanse(String expanse) {
    var value = getExpanseValue(expanse);
    assertions.hasAttribute("expanse", value);
  }

  public void hasExpanse(ExpanseBase expanse) {
    hasExpanse(expanse.toString());
  }

  public void hasExpanse(Expanse expanse) {
    hasExpanse(expanse.name());
  }

  public void hasExpanse(AvatarExpanse expanse) {
    hasExpanse(expanse.name());
  }

  public void hasSource(String value) {
    assertions.hasAttribute("src", value);
  }

  public void hasHorizontalAlignment(HasHorizontalAlignment.Alignment alignment) {
    hasHorizontalAlignment(assertions, alignment);
  }

  /*@Override
  public void isChecked(IsCheckedOptions options) {
    var attributeOptions = new HasAttributeOptions();
    if (options == null) {
      options = new IsCheckedOptions();
    } else if (options.timeout != null) {
      attributeOptions.setTimeout(options.timeout);
    }

    if (options.indeterminate != null && options.indeterminate) {
      // When checking for indeterminate state, check for the indeterminate attribute
      assertions.hasAttribute("indeterminate", "", attributeOptions);
    } else {
      // When checking for checked state, check for the checked attribute
      assertions.hasAttribute("checked", "", attributeOptions);
    }
  }*/
}
