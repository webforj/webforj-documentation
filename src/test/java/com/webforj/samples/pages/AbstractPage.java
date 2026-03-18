package com.webforj.samples.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import com.webforj.component.Composite;
import com.webforj.component.ExpanseBase;
import com.webforj.component.ThemeBase;
import com.webforj.samples.config.RunConfig;
import com.webforj.samples.utils.NodeNameUtils;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.utils.WebforjAssertions;
import com.webforj.samples.utils.WebforjLocator;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Base class for page objects in webforj integration tests.
 *
 * <p>Provides convenience methods for creating {@link WebforjLocator} instances and grants access to
 * commonly used utility methods for testing webforj applications.
 */
public abstract class AbstractPage {
  protected final Page page;
  private final String route;

  public AbstractPage(Page page, String route) {
    this.page = page;
    this.route = route;
  }

  public AbstractPage(Page page, Class<? extends Composite<?>> view) {
    this(page, view.getSimpleName().replace("View", "").toLowerCase());
  }

  public void setRoute(SupportedLanguage language) {
    page.navigate("http://localhost:" + RunConfig.getPort() + "/" + language.getPath(route));
    // Wait for the page to be fully loaded
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);
  }

  public WebforjLocator getByClass(Class<?>... klass) {
    return locator(Arrays.stream(klass)
      .map(NodeNameUtils::getNodeName)
      .collect(Collectors.joining(" ")));
  }

  public WebforjLocator getByClass(Class<?> klass) {
    return locator(NodeNameUtils.getNodeName(klass));
  }

  public WebforjLocator getButton(String text) {
    return getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text));
  }

  public WebforjLocator getCheckbox(String text) {
    return getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(text));
  }

  public WebforjLocator getByAttribute(Class<?> klass, String attribute, String value) {
    String nodeName = NodeNameUtils.getNodeName(klass);
    String selector = "%s[%s='%s']".formatted(nodeName, attribute, value);
    return locator(selector);
  }

  public WebforjLocator getByTheme(Class<?> klass, ThemeBase theme) {
    return getByAttribute(klass, "theme", WebforjAssertions.getThemeValue(theme));
  }

  public WebforjLocator getByExpanse(Class<?> klass, ExpanseBase expanse) {
    return getByAttribute(klass, "expanse", WebforjAssertions.getExpanseValue(expanse));
  }

  public WebforjLocator getByAltText(String text) {
    return getByAltText(text, null);
  }

  public WebforjLocator getByAltText(String text, Page.GetByAltTextOptions options) {
    return new WebforjLocator(page.getByAltText(text, options));
  }

  public WebforjLocator getByAltText(Pattern text) {
    return getByAltText(text, null);
  }

  public WebforjLocator getByAltText(Pattern text, Page.GetByAltTextOptions options) {
    return new WebforjLocator(page.getByAltText(text, options));
  }

  public WebforjLocator getByLabel(String text) {
    return getByLabel(text, null);
  }

  public WebforjLocator getByLabel(String text, Page.GetByLabelOptions options) {
    return new WebforjLocator(page.getByLabel(text, options));
  }

  public WebforjLocator getByLabel(Pattern text) {
    return getByLabel(text, null);
  }

  public WebforjLocator getByLabel(Pattern text, Page.GetByLabelOptions options) {
    return new WebforjLocator(page.getByLabel(text, options));
  }

  public WebforjLocator getByPlaceholder(String text) {
    return getByPlaceholder(text, null);
  }

  public WebforjLocator getByPlaceholder(String text, Page.GetByPlaceholderOptions options) {
    return new WebforjLocator(page.getByPlaceholder(text, options));
  }

  public WebforjLocator getByPlaceholder(Pattern text) {
    return getByPlaceholder(text, null);
  }

  public WebforjLocator getByPlaceholder(Pattern text, Page.GetByPlaceholderOptions options) {
    return new WebforjLocator(page.getByPlaceholder(text, options));
  }

  public WebforjLocator getByRole(AriaRole role) {
    return getByRole(role, null);
  }

  public WebforjLocator getByRole(AriaRole role, Page.GetByRoleOptions options) {
    return new WebforjLocator(page.getByRole(role, options));
  }

  public WebforjLocator getByTestId(String testId) {
    return new WebforjLocator(page.getByTestId(testId));
  }

  public WebforjLocator getByTestId(Pattern testId) {
    return new WebforjLocator(page.getByTestId(testId));
  }

  public WebforjLocator getByText(String text) {
    return getByText(text, null);
  }

  public WebforjLocator getByText(String text, Page.GetByTextOptions options) {
    return new WebforjLocator(page.getByText(text, options));
  }

  public WebforjLocator getByText(Pattern text) {
    return getByText(text, null);
  }

  public WebforjLocator getByText(Pattern text, Page.GetByTextOptions options) {
    return new WebforjLocator(page.getByText(text, options));
  }

  public WebforjLocator getByTitle(String text) {
    return getByTitle(text, null);
  }

  public WebforjLocator getByTitle(String text, Page.GetByTitleOptions options) {
    return new WebforjLocator(page.getByTitle(text, options));
  }

  public WebforjLocator getByTitle(Pattern text) {
    return getByTitle(text, null);
  }

  public WebforjLocator getByTitle(Pattern text, Page.GetByTitleOptions options) {
    return new WebforjLocator(page.getByTitle(text, options));
  }

  public WebforjLocator locator(String selector) {
    return locator(selector, null);
  }

  public WebforjLocator locator(String selector, Page.LocatorOptions options) {
    return new WebforjLocator(page.locator(selector));
  }

}
