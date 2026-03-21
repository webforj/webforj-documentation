package com.webforj.samples.utils.components;

import com.microsoft.playwright.Locator;
import com.webforj.component.Expanse;
import com.webforj.component.Theme;
import com.webforj.samples.utils.WebforjLocator;

/**
 * A utility class for testing webforj Alert components.
 *
 * <p>This class extends {@link AbstractComponent} and provides convenient access to alert
 * shadow parts and attribute assertions.
 *
 * <p>The Alert component exposes the following shadow parts:
 * <ul>
 *   <li>content - The main content area of the alert</li>
 *   <li>icon - The alert icon</li>
 *   <li>close - The close button (if the alert is closable)</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * AlertComponent alert = new AlertComponent(getByClass(Alert.class).first());
 * alert.assertTheme(Theme.SUCCESS);
 * alert.assertExpanse(Expanse.MEDIUM);
 * alert.getCloseButton().click(); // Close the alert
 * }</pre>
 */
public class AlertComponent extends AbstractComponent {

  /**
   * Creates a new AlertComponent wrapping the given locator.
   *
   * @param locator a locator pointing to a dwc-alert element
   */
  public AlertComponent(Locator locator) {
    super(locator);
  }

  /**
   * Returns a locator for the alert's content shadow part.
   *
   * <p>The content is the main area containing the alert message.
   *
   * @return a locator targeting the content part
   */
  public WebforjLocator getContent() {
    return getShadowPart("content");
  }

  /**
   * Returns a locator for the alert's icon shadow part.
   *
   * @return a locator targeting the icon part
   */
  public WebforjLocator getIcon() {
    return getShadowPart("icon");
  }

  /**
   * Returns a locator for the alert's close button shadow part.
   *
   * <p>This locator will only match if the alert is closable.
   *
   * @return a locator targeting the close button part
   */
  public WebforjLocator getCloseButton() {
    return getShadowPart("close");
  }

  /**
   * Asserts that the alert has the specified theme attribute.
   *
   * @param theme the expected theme value
   */
  public void assertTheme(Theme theme) {
    locator.assertThat().hasTheme(theme);
  }

  /**
   * Asserts that the alert has the specified expanse attribute.
   *
   * @param expanse the expected expanse value
   */
  public void assertExpanse(Expanse expanse) {
    locator.assertThat().hasExpanse(expanse);
  }

  /**
   * Asserts that the alert is visible.
   */
  public void assertVisible() {
    locator.assertThat().isVisible();
  }
}
