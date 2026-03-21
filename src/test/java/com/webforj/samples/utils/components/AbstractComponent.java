package com.webforj.samples.utils.components;

import com.microsoft.playwright.Locator;
import com.webforj.samples.utils.WebforjLocator;

public abstract class AbstractComponent {
  protected final WebforjLocator locator;

  protected AbstractComponent(Locator locator) {
    if (locator instanceof WebforjLocator) {
      this.locator = (WebforjLocator) locator;
    } else {
      this.locator = new WebforjLocator(locator);
    }
  }

  /**
   * Returns the underlying WebforjLocator for this component.
   *
   * @return the wrapped locator
   */
  public WebforjLocator getLocator() {
    return locator;
  }

  /**
   * Returns a locator for a shadow part by its part name.
   *
   * <p>Playwright CSS selectors pierce shadow DOM automatically, so we can use [part="..."]
   * selectors directly.
   *
   * @param partName the name of the shadow part (e.g., "label", "prefix", "suffix")
   * @return a locator targeting the specified shadow part
   */
  protected WebforjLocator getShadowPart(String partName) {
    return locator.locator("part=['" + partName + "']");
  }

  /**
   * Asserts that the component is visible.
   */
  public void assertIsVisible() {
    locator.assertThat().isVisible();
  }

  /**
   * Asserts that the component is not visible.
   */
  public void assertNotVisible() {
    locator.assertThat().not().isVisible();
  }
}
