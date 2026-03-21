package com.webforj.samples.utils.components;

import com.microsoft.playwright.Locator;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.component.avatar.AvatarShape;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.samples.utils.WebforjLocator;

/**
 * A utility class for testing webforj Avatar components.
 *
 * <p>This class extends {@link AbstractComponent} and provides convenient access to avatar
 * shadow parts and attribute assertions.
 *
 * <p>The Avatar component exposes the following shadow parts:
 * <ul>
 *   <li>image - The avatar image (if present)</li>
 *   <li>initials - The avatar initials (if no image)</li>
 *   <li>icon - The avatar icon (fallback when no image or initials)</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * AvatarComponent avatar = new AvatarComponent(getByClass(Avatar.class).first());
 * avatar.assertTheme(AvatarTheme.SUCCESS);
 * avatar.assertExpanse(AvatarExpanse.MEDIUM);
 * avatar.assertShape(AvatarShape.CIRCLE);
 * }</pre>
 */
public class AvatarComponent extends AbstractComponent {

  /**
   * Creates a new AvatarComponent wrapping the given locator.
   *
   * @param locator a locator pointing to a dwc-avatar element
   */
  public AvatarComponent(Locator locator) {
    super(locator);
  }

  /**
   * Returns a locator for the avatar's image shadow part.
   *
   * <p>The image is displayed when the avatar has an image source set.
   *
   * @return a locator targeting the image part
   */
  public WebforjLocator getImage() {
    return getShadowPart("image");
  }

  /**
   * Returns a locator for the avatar's initials shadow part.
   *
   * <p>The initials are displayed when no image is available.
   *
   * @return a locator targeting the initials part
   */
  public WebforjLocator getInitials() {
    return getShadowPart("initials");
  }

  /**
   * Returns a locator for the avatar's icon shadow part.
   *
   * <p>The icon is displayed as a fallback when no image or initials are available.
   *
   * @return a locator targeting the icon part
   */
  public WebforjLocator getIcon() {
    return getShadowPart("icon");
  }

  /**
   * Asserts that the avatar has the specified theme attribute.
   *
   * @param theme the expected theme value
   */
  public void assertTheme(AvatarTheme theme) {
    locator.assertThat().hasTheme(theme);
  }

  /**
   * Asserts that the avatar has the specified expanse attribute.
   *
   * @param expanse the expected expanse value
   */
  public void assertExpanse(AvatarExpanse expanse) {
    locator.assertThat().hasExpanse(expanse);
  }

  /**
   * Asserts that the avatar has the specified shape attribute.
   *
   * @param shape the expected shape value
   */
  public void assertShape(AvatarShape shape) {
    locator.assertThat().hasAttribute("shape", shape.name().toLowerCase());
  }

  /**
   * Clicks the avatar.
   */
  public void click() {
    locator.click();
  }

  /**
   * Clicks the avatar with options.
   *
   * @param options click options
   */
  public void click(Locator.ClickOptions options) {
    locator.click(options);
  }
}
