package com.webforj.samples.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.SelectOption;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A delegating {@link Locator} implementation that wraps Playwright's Locator to provide webforj-specific
 * enhancements.
 *
 * <p>This class serves as an extension point for adding custom locator methods tailored for webforj components.
 * All operations are delegated to the underlying {@link Locator} instance while returning
 * {@link WebforjLocator} for method chaining.
 */
public class WebforjLocator implements Locator {
  private final Locator locator;

  public WebforjLocator(Locator locator) {
    this.locator = locator;
  }

  public WebforjAssertions assertThat() {
    return new WebforjAssertions(PlaywrightAssertions.assertThat(this), this);
  }

  public List<WebforjLocator> allAsWebforj() {
    return all().stream().map(WebforjLocator.class::cast).toList();
  }

  @Override
  public List<Locator> all() {
    return locator.all();
  }

  @Override
  public List<String> allInnerTexts() {
    return locator.allInnerTexts();
  }

  @Override
  public List<String> allTextContents() {
    return locator.allTextContents();
  }

  @Override
  public WebforjLocator and(Locator locator) {
    return new WebforjLocator(this.locator.and(locator));
  }

  @Override
  public String ariaSnapshot(AriaSnapshotOptions options) {
    return "";
  }

  @Override
  public void blur(BlurOptions options) {

  }

  @Override
  public BoundingBox boundingBox() {
    return locator.boundingBox();
  }

  @Override
  public BoundingBox boundingBox(BoundingBoxOptions options) {
    return locator.boundingBox(options);
  }

  @Override
  public void check(CheckOptions options) {

  }

  @Override
  public void clear(ClearOptions options) {
    locator.clear(options);
  }

  @Override
  public void click(ClickOptions options) {
    locator.click(options);
  }

  @Override
  public int count() {
    return locator.count();
  }

  @Override
  public void dblclick(DblclickOptions options) {

  }

  @Override
  public void dispatchEvent(String type, Object eventInit, DispatchEventOptions options) {
    locator.dispatchEvent(type, eventInit, options);
  }

  @Override
  public void dragTo(Locator target, DragToOptions options) {
    locator.dragTo(target, options);
  }

  @Override
  public FrameLocator contentFrame() {
    return locator.contentFrame();
  }

  @Override
  public ElementHandle elementHandle() {
    return locator.elementHandle();
  }

  @Override
  public ElementHandle elementHandle(ElementHandleOptions options) {
    return locator.elementHandle(options);
  }

  @Override
  public List<ElementHandle> elementHandles() {
    return locator.elementHandles();
  }

  @Override
  public Object evaluate(String expression, Object arg) {
    return locator.evaluate(expression, arg);
  }

  @Override
  public Object evaluate(String expression) {
    return locator.evaluate(expression);
  }

  @Override
  public Object evaluate(String expression, Object arg, EvaluateOptions options) {
    return locator.evaluate(expression, arg, options);
  }

  @Override
  public Object evaluateAll(String expression) {
    return locator.evaluateAll(expression);
  }

  @Override
  public Object evaluateAll(String expression, Object arg) {
    return locator.evaluateAll(expression, arg);
  }

  @Override
  public JSHandle evaluateHandle(String expression, Object arg) {
    return locator.evaluateHandle(expression, arg);
  }

  @Override
  public JSHandle evaluateHandle(String expression) {
    return locator.evaluateHandle(expression);
  }

  @Override
  public JSHandle evaluateHandle(String expression, Object arg, EvaluateHandleOptions options) {
    return locator.evaluateHandle(expression, arg, options);
  }

  @Override
  public void fill(String value, FillOptions options) {
    locator.fill(value, options);
  }

  @Override
  public WebforjLocator filter() {
    return new WebforjLocator(locator.filter());
  }

  @Override
  public WebforjLocator filter(FilterOptions options) {
    return new WebforjLocator(locator.filter(options));
  }

  @Override
  public WebforjLocator first() {
    return new WebforjLocator(locator.first());
  }

  @Override
  public void focus(FocusOptions options) {

  }

  @Override
  public FrameLocator frameLocator(String selector) {
    return locator.frameLocator(selector);
  }

  @Override
  public String getAttribute(String name) {
    return locator.getAttribute(name);
  }

  @Override
  public String getAttribute(String name, GetAttributeOptions options) {
    return locator.getAttribute(name, options);
  }

  @Override
  public WebforjLocator getByAltText(String text) {
    return new WebforjLocator(locator.getByAltText(text));
  }

  @Override
  public WebforjLocator getByAltText(String text, GetByAltTextOptions options) {
    return new WebforjLocator(locator.getByAltText(text, options));
  }

  @Override
  public WebforjLocator getByAltText(Pattern text) {
    return new WebforjLocator(locator.getByAltText(text));
  }

  @Override
  public WebforjLocator getByAltText(Pattern text, GetByAltTextOptions options) {
    return new WebforjLocator(locator.getByAltText(text, options));
  }

  @Override
  public WebforjLocator getByLabel(String text) {
    return new WebforjLocator(locator.getByLabel(text));
  }

  @Override
  public WebforjLocator getByLabel(String text, GetByLabelOptions options) {
    return new WebforjLocator(locator.getByLabel(text, options));
  }

  @Override
  public WebforjLocator getByLabel(Pattern text) {
    return new WebforjLocator(locator.getByLabel(text));
  }

  @Override
  public WebforjLocator getByLabel(Pattern text, GetByLabelOptions options) {
    return new WebforjLocator(locator.getByLabel(text, options));
  }

  @Override
  public WebforjLocator getByPlaceholder(String text) {
    return new WebforjLocator(locator.getByPlaceholder(text));
  }

  @Override
  public WebforjLocator getByPlaceholder(String text, GetByPlaceholderOptions options) {
    return new WebforjLocator(locator.getByPlaceholder(text, options));
  }

  @Override
  public WebforjLocator getByPlaceholder(Pattern text) {
    return new WebforjLocator(locator.getByPlaceholder(text));
  }

  @Override
  public WebforjLocator getByPlaceholder(Pattern text, GetByPlaceholderOptions options) {
    return new WebforjLocator(locator.getByPlaceholder(text, options));
  }

  @Override
  public WebforjLocator getByRole(AriaRole role) {
    return new WebforjLocator(locator.getByRole(role));
  }

  @Override
  public WebforjLocator getByRole(AriaRole role, GetByRoleOptions options) {
    return new WebforjLocator(locator.getByRole(role, options));
  }

  @Override
  public WebforjLocator getByTestId(String testId) {
    return new WebforjLocator(locator.getByTestId(testId));
  }

  @Override
  public WebforjLocator getByTestId(Pattern testId) {
    return new WebforjLocator(locator.getByTestId(testId));
  }

  @Override
  public WebforjLocator getByText(String text) {
    return new WebforjLocator(locator.getByText(text));
  }

  @Override
  public WebforjLocator getByText(String text, GetByTextOptions options) {
    return new WebforjLocator(locator.getByText(text, options));
  }

  @Override
  public WebforjLocator getByText(Pattern text) {
    return new WebforjLocator(locator.getByText(text));
  }

  @Override
  public WebforjLocator getByText(Pattern text, GetByTextOptions options) {
    return new WebforjLocator(locator.getByText(text, options));
  }

  @Override
  public WebforjLocator getByTitle(String text) {
    return new WebforjLocator(locator.getByTitle(text));
  }

  @Override
  public WebforjLocator getByTitle(String text, GetByTitleOptions options) {
    return new WebforjLocator(locator.getByTitle(text, options));
  }

  @Override
  public WebforjLocator getByTitle(Pattern text) {
    return new WebforjLocator(locator.getByTitle(text));
  }

  @Override
  public WebforjLocator getByTitle(Pattern text, GetByTitleOptions options) {
    return new WebforjLocator(locator.getByTitle(text, options));
  }

  @Override
  public void highlight() {

  }

  @Override
  public void hover(HoverOptions options) {

  }

  @Override
  public String innerHTML() {
    return locator.innerHTML();
  }

  @Override
  public String innerHTML(InnerHTMLOptions options) {
    return locator.innerHTML(options);
  }

  @Override
  public String innerText() {
    return locator.innerText();
  }

  @Override
  public String innerText(InnerTextOptions options) {
    return locator.innerText(options);
  }

  @Override
  public String inputValue() {
    return locator.inputValue();
  }

  @Override
  public String inputValue(InputValueOptions options) {
    return locator.inputValue(options);
  }

  @Override
  public boolean isChecked(IsCheckedOptions options) {
    return locator.isChecked(options);
  }

  @Override
  public boolean isDisabled(IsDisabledOptions options) {
    return locator.isDisabled(options);
  }

  @Override
  public boolean isEnabled(IsEnabledOptions options) {
    return locator.isEnabled(options);
  }

  @Override
  public boolean isEditable(IsEditableOptions options) {
    return locator.isEditable(options);
  }

  @Override
  public boolean isHidden(IsHiddenOptions options) {
    return locator.isHidden(options);
  }

  @Override
  public boolean isVisible(IsVisibleOptions options) {
    return locator.isVisible(options);
  }

  @Override
  public WebforjLocator last() {
    return new WebforjLocator(locator.last());
  }

  @Override
  public WebforjLocator locator(String selectorOrLocator) {
    return new WebforjLocator(locator.locator(selectorOrLocator));
  }

  @Override
  public WebforjLocator locator(String selectorOrLocator, LocatorOptions options) {
    return new WebforjLocator(locator.locator(selectorOrLocator, options));
  }

  @Override
  public WebforjLocator locator(Locator selectorOrLocator) {
    return new WebforjLocator(locator.locator(selectorOrLocator));
  }

  @Override
  public WebforjLocator locator(Locator selectorOrLocator, LocatorOptions options) {
    return new WebforjLocator(locator.locator(selectorOrLocator, options));
  }

  @Override
  public WebforjLocator nth(int index) {
    return new WebforjLocator(locator.nth(index));
  }

  @Override
  public WebforjLocator or(Locator locator) {
    return new WebforjLocator(this.locator.or(locator));
  }

  @Override
  public Page page() {
    return locator.page();
  }

  @Override
  public void press(String key, PressOptions options) {
    locator.press(key, options);
  }

  @Override
  public void pressSequentially(String text, PressSequentiallyOptions options) {
    locator.pressSequentially(text, options);
  }

  @Override
  public byte[] screenshot() {
    return locator.screenshot();
  }

  @Override
  public byte[] screenshot(ScreenshotOptions options) {
    return locator.screenshot(options);
  }

  @Override
  public void scrollIntoViewIfNeeded(ScrollIntoViewIfNeededOptions options) {
    locator.scrollIntoViewIfNeeded(options);
  }

  @Override
  public List<String> selectOption(String values) {
    return locator.selectOption(values);
  }

  @Override
  public List<String> selectOption(String values, SelectOptionOptions options) {
    return locator.selectOption(values, options);
  }

  @Override
  public List<String> selectOption(ElementHandle values) {
    return locator.selectOption(values);
  }

  @Override
  public List<String> selectOption(ElementHandle values, SelectOptionOptions options) {
    return locator.selectOption(values, options);
  }

  @Override
  public List<String> selectOption(String[] values) {
    return locator.selectOption(values);
  }

  @Override
  public List<String> selectOption(String[] values, SelectOptionOptions options) {
    return locator.selectOption(values, options);
  }

  @Override
  public List<String> selectOption(SelectOption values) {
    return locator.selectOption(values);
  }

  @Override
  public List<String> selectOption(SelectOption values, SelectOptionOptions options) {
    return locator.selectOption(values, options);
  }

  @Override
  public List<String> selectOption(ElementHandle[] values) {
    return locator.selectOption(values);
  }

  @Override
  public List<String> selectOption(ElementHandle[] values, SelectOptionOptions options) {
    return locator.selectOption(values, options);
  }

  @Override
  public List<String> selectOption(SelectOption[] values) {
    return locator.selectOption(values);
  }

  @Override
  public List<String> selectOption(SelectOption[] values, SelectOptionOptions options) {
    return locator.selectOption(values, options);
  }

  @Override
  public void selectText(SelectTextOptions options) {
    locator.selectText(options);
  }

  @Override
  public void setChecked(boolean checked, SetCheckedOptions options) {
    locator.setChecked(checked, options);
  }

  @Override
  public void setInputFiles(Path files, SetInputFilesOptions options) {
    locator.setInputFiles(files, options);
  }

  @Override
  public void setInputFiles(Path[] files, SetInputFilesOptions options) {
    locator.setInputFiles(files, options);
  }

  @Override
  public void setInputFiles(FilePayload files, SetInputFilesOptions options) {
    locator.setInputFiles(files, options);
  }

  @Override
  public void setInputFiles(FilePayload[] files, SetInputFilesOptions options) {
    locator.setInputFiles(files, options);
  }

  @Override
  public void tap(TapOptions options) {

  }

  @Override
  public String textContent() {
    return locator.textContent();
  }

  @Override
  public String textContent(TextContentOptions options) {
    return locator.textContent(options);
  }

  @Override
  public void type(String text, TypeOptions options) {
    locator.type(text, options);
  }

  @Override
  public void uncheck(UncheckOptions options) {
    locator.uncheck(options);
  }

  @Override
  public void waitFor(WaitForOptions options) {
    locator.waitFor(options);
  }
}
