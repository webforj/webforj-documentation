package com.webforj.samples.utils.components;

import com.microsoft.playwright.Locator;
import com.webforj.component.Expanse;
import com.webforj.component.button.ButtonTheme;
import com.webforj.samples.utils.WebforjLocator;

/**
 * A utility class for testing webforj ChoiceBox components.
 *
 * <p>This class extends {@link ButtonComponent} since ChoiceBox shares many parts and attributes
 * with buttons (prefix, label, suffix, theme, expanse). It adds ChoiceBox-specific functionality
 * for interacting with the dropdown and selecting items.
 *
 * <p>The ChoiceBox component exposes the following shadow parts:
 * <ul>
 *   <li>control - The dropdown trigger/button</li>
 *   <li>prefix - Content before the label (inherited from button)</li>
 *   <li>label - The displayed text (inherited from button)</li>
 *   <li>suffix - Content after the label (inherited from button)</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * ChoiceBoxComponent choiceBox = new ChoiceBoxComponent(getByClass(ChoiceBox.class).first());
 *
 * // Interact with the dropdown control
 * choiceBox.getControl().click(); // Opens the dropdown
 * choiceBox.selectByText("Option 2");
 *
 * // Assert attributes
 * choiceBox.assertTheme(ButtonTheme.PRIMARY);
 * choiceBox.assertExpanse(Expanse.MEDIUM);
 * }</pre>
 */
public class ChoiceBoxComponent extends ButtonComponent {

  /**
   * Creates a new ChoiceBoxComponent wrapping the given locator.
   *
   * @param locator a locator pointing to a dwc-choicebox element
   */
  public ChoiceBoxComponent(Locator locator) {
    super(locator);
  }

  /**
   * Returns a locator for the ChoiceBox's control shadow part.
   *
   * <p>The control is the dropdown trigger button that opens the list of options when clicked.
   *
   * @return a locator targeting the control part
   */
  public WebforjLocator getControl() {
    return getShadowPart("button");
  }

  /**
   * Opens the dropdown by clicking on the control.
   */
  public void open() {
    getControl().click();
  }

  /**
   * Opens the dropdown by clicking on the control with options.
   *
   * @param options click options
   */
  public void open(Locator.ClickOptions options) {
    getControl().click(options);
  }

  /**
   * Selects an item from the dropdown by its visible text.
   *
   * <p>This method opens the dropdown, waits for options to appear, clicks the matching option,
   * and waits for the dropdown to close.
   *
   * @param text the visible text of the option to select
   */
  public void selectByText(String text) {
    open();
    locator.getByText(text).click();
  }

  /**
   * Selects an item from the dropdown by its index.
   *
   * <p>This method opens the dropdown, waits for options to appear, clicks the option at the
   * specified index, and waits for the dropdown to close.
   *
   * @param index the zero-based index of the option to select
   */
  public void selectByIndex(int index) {
    open();
    locator.locator("li").nth(index).click();
  }

  /**
   * Returns a locator for an option in the dropdown by its text.
   *
   * <p>Note: The dropdown must be open for this to work correctly.
   *
   * @param text the visible text of the option
   * @return a locator targeting the option element
   */
  public WebforjLocator getOption(String text) {
    return locator.getByText(text);
  }

  /**
   * Returns a locator for an option in the dropdown by its index.
   *
   * <p>Note: The dropdown must be open for this to work correctly.
   *
   * @param index the zero-based index of the option
   * @return a locator targeting the option element
   */
  public WebforjLocator getOptionByIndex(int index) {
    return locator.locator("li").nth(index);
  }

  /**
   * Asserts that the ChoiceBox has the specified theme attribute.
   *
   * @param theme the expected theme value
   */
  @Override
  public void assertTheme(ButtonTheme theme) {
    locator.assertThat().hasTheme(theme);
  }

  /**
   * Asserts that the ChoiceBox has the specified expanse attribute.
   *
   * @param expanse the expected expanse value
   */
  @Override
  public void assertExpanse(Expanse expanse) {
    locator.assertThat().hasExpanse(expanse);
  }

  /**
   * Asserts that the ChoiceBox is disabled.
   */
  @Override
  public void assertDisabled() {
    locator.assertThat().isDisabled();
  }

  /**
   * Asserts that the ChoiceBox is enabled (not disabled).
   */
  @Override
  public void assertEnabled() {
    locator.assertThat().not().isDisabled();
  }
}
