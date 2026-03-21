package com.webforj.samples.utils.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.Expanse;
import com.webforj.component.button.ButtonTheme;
import com.webforj.samples.utils.WebforjLocator;

import java.util.regex.Pattern;

/**
 * A utility class for testing webforj Button components.
 *
 * <p>This class provides convenient access to button shadow parts and attribute assertions.
 * It wraps a {@link Locator} pointing to a button element and provides methods to:
 * <ul>
 *   <li>Access shadow parts (prefix, label, suffix)</li>
 *   <li>Assert reflected attributes (expanse, theme, disabled)</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * ButtonComponent button = new ButtonComponent(page, getByClass(Button.class).first());
 * button.getLabel().assertThat().hasText("Submit");
 * button.assertExpanse(Expanse.LARGE);
 * button.assertTheme(ButtonTheme.PRIMARY);
 * }</pre>
 */
public class ButtonComponent extends AbstractComponent {

  /**
   * Creates a new ButtonComponent wrapping the given locator.
   *
   * @param locator a locator pointing to a dwc-button element
   */
  public ButtonComponent(Locator locator) {
    super(locator);
  }

  /**
   * Returns a locator for the button's label shadow part.
   *
   * <p>The label is the span element with part="label" inside the button's shadow DOM.
   *
   * @return a locator targeting the label part containing the button text
   */
  public WebforjLocator getLabel() {
    return getShadowPart("label");
  }

  /**
   * Returns a locator for the button's prefix shadow part.
   *
   * <p>The prefix is the span element with part="prefix" inside the button's shadow DOM,
   * typically used for icons before the button text.
   *
   * @return a locator targeting the prefix part
   */
  public WebforjLocator getPrefix() {
    return getShadowPart("prefix");
  }

  /**
   * Returns a locator for the button's suffix shadow part.
   *
   * <p>The suffix is the span element with part="suffix" inside the button's shadow DOM,
   * typically used for icons after the button text.
   *
   * @return a locator targeting the suffix part
   */
  public WebforjLocator getSuffix() {
    return getShadowPart("suffix");
  }

  /**
   * Asserts that the button has the specified expanse attribute.
   *
   * @param expanse the expected expanse value
   */
  public void assertExpanse(Expanse expanse) {
    locator.assertThat().hasExpanse(expanse);
  }

  /**
   * Asserts that the button has the specified theme attribute.
   *
   * @param theme the expected theme value
   */
  public void assertTheme(ButtonTheme theme) {
    locator.assertThat().hasTheme(theme);
  }

  /**
   * Asserts that the button is disabled.
   */
  public void assertDisabled() {
    locator.assertThat().isDisabled();
  }

  /**
   * Asserts that the button is enabled (not disabled).
   */
  public void assertEnabled() {
    locator.assertThat().not().isDisabled();
  }

  /**
   * Asserts that the button has the specified text content in its label.
   *
   * @param text the expected text content
   */
  public void assertText(String text) {
    locator.assertThat().hasText(text);
  }

  /**
   * Asserts that the button has the specified text content in its label (using regex pattern).
   *
   * @param pattern a regex pattern to match against the label text
   */
  public void assertText(Pattern pattern) {
    locator.assertThat().hasText(pattern);
  }

  /**
   * Clicks the button.
   */
  public void click() {
    locator.click();
  }

  /**
   * Clicks the button with options.
   *
   * @param options click options
   */
  public void click(Locator.ClickOptions options) {
    locator.click(options);
  }
}
