package com.webforj.samples.utils.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.component.Expanse;
import com.webforj.concern.HasHorizontalAlignment;
import com.webforj.samples.utils.WebforjLocator;

/**
 * A utility class for testing webforj CheckBox components.
 *
 * <p>This class extends {@link AbstractComponent} and provides convenient access to checkbox
 * shadow parts and attribute assertions.
 *
 * <p>The CheckBox component exposes the following shadow parts:
 * <ul>
 *   <li>control - The component's base wrapper</li>
 *   <li>input-wrapper - The input's wrapper</li>
 *   <li>input - The actual input</li>
 *   <li>label - The input's label</li>
 *   <li>checked-icon - The checked icon wrapper</li>
 *   <li>indeterminate-icon - The indeterminate icon wrapper</li>
 *   <li>helper-text - The helper text part</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * CheckboxComponent checkbox = new CheckboxComponent(getByClass(CheckBox.class).first());
 * checkbox.assertChecked(true);
 * checkbox.assertExpanse(Expanse.MEDIUM);
 * checkbox.getLabel().assertThat().hasText("Accept terms");
 * }</pre>
 */
public class CheckboxComponent extends AbstractComponent {

  /**
   * Creates a new CheckboxComponent wrapping the given locator.
   *
   * @param locator a locator pointing to a dwc-checkbox element
   */
  public CheckboxComponent(Locator locator) {
    super(locator);
  }

  /**
   * Returns a locator for the checkbox's input element.
   *
   * <p>The input is the actual checkbox input element.
   *
   * @return a locator targeting the input part
   */
  public WebforjLocator getInput() {
    return locator.getByRole(AriaRole.CHECKBOX);
  }

  /**
   * Returns a locator for the checkbox's input wrapper element.
   *
   * <p>The input wrapper contains the input and icons.
   *
   * @return a locator targeting the input-wrapper part
   */
  public WebforjLocator getInputWrapper() {
    return getShadowPart("input-wrapper");
  }

  /**
   * Returns a locator for the checkbox's label element.
   *
   * <p>The label displays the checkbox text.
   *
   * @return a locator targeting the label part
   */
  public WebforjLocator getLabel() {
    return getShadowPart("label");
  }

  /**
   * Returns a locator for the checkbox's checked icon element.
   *
   * <p>The checked icon is displayed when the checkbox is checked.
   *
   * @return a locator targeting the checked-icon part
   */
  public WebforjLocator getCheckedIcon() {
    return getShadowPart("checked-icon");
  }

  /**
   * Returns a locator for the checkbox's indeterminate icon element.
   *
   * <p>The indeterminate icon is displayed when the checkbox is in indeterminate state.
   *
   * @return a locator targeting the indeterminate-icon part
   */
  public WebforjLocator getIndeterminateIcon() {
    return getShadowPart("indeterminate-icon");
  }

  /**
   * Returns a locator for the checkbox's helper text element.
   *
   * @return a locator targeting the helper-text part
   */
  public WebforjLocator getHelperText() {
    return getShadowPart("helper-text");
  }

  /**
   * Asserts that the checkbox is checked.
   *
   * @param checked true if the checkbox should be checked
   */
  public void assertChecked(boolean checked) {
    if (checked) {
      getInput().assertThat().isChecked();
//      locator.assertThat().hasAttribute("checked", "");
    } else {
      getInput().assertThat().not().isChecked();
//      locator.assertThat().not().hasAttribute("checked", "");
    }
  }

  /**
   * Asserts that the checkbox is in indeterminate state.
   *
   * @param indeterminate true if the checkbox should be indeterminate
   */
  public void assertIndeterminate(boolean indeterminate) {
    if (indeterminate) {
      getInput().assertThat().isChecked(new LocatorAssertions.IsCheckedOptions().setIndeterminate(true));
//      locator.assertThat().hasAttribute("indeterminate", "");
    } else {
      getInput().assertThat().not().isChecked(new LocatorAssertions.IsCheckedOptions().setIndeterminate(true));
//      locator.assertThat().not().hasAttribute("indeterminate", "null");
    }
  }

  /**
   * Asserts that the checkbox has the specified expanse attribute.
   *
   * @param expanse the expected expanse value
   */
  public void assertExpanse(Expanse expanse) {
    locator.assertThat().hasExpanse(expanse);
  }

  /**
   * Asserts that the checkbox is disabled.
   *
   * @param disabled true if the checkbox should be disabled
   */
  public void assertDisabled(boolean disabled) {
    if (disabled) {
      locator.assertThat().hasAttribute("disabled", "");
    } else {
      locator.assertThat().not().hasAttribute("disabled", "");
    }
  }

  public void assertHorizontalAlignment(HasHorizontalAlignment.Alignment alignment) {
    locator.assertThat().hasHorizontalAlignment(alignment);
  }

  /**
   * Clicks the checkbox.
   */
  public void click() {
    locator.click();
  }

  /**
   * Clicks the checkbox with options.
   *
   * @param options click options
   */
  public void click(Locator.ClickOptions options) {
    locator.click(options);
  }

  /**
   * Checks the checkbox by clicking if it's not already checked.
   */
  public void check() {
    if (!isChecked()) {
      click();
    }
  }

  /**
   * Unchecks the checkbox by clicking if it's not already unchecked.
   */
  public void uncheck() {
    if (isChecked() || isIndeterminate()) {
      click();
    }
  }

  /**
   * Returns whether the checkbox is checked.
   *
   * @return true if the checkbox is checked
   */
  public boolean isChecked() {
    return locator.getAttribute("checked") != null;
  }

  /**
   * Returns whether the checkbox is in indeterminate state.
   *
   * @return true if the checkbox is indeterminate
   */
  public boolean isIndeterminate() {
    return locator.getAttribute("indeterminate") != null;
  }

  /**
   * Returns whether the checkbox is disabled.
   *
   * @return true if the checkbox is disabled
   */
  public boolean isDisabled() {
    return locator.getAttribute("disabled") != null;
  }
}
