package com.webforj.samples.views.checkbox;

import com.webforj.samples.pages.checkbox.CheckboxIndeterminatePage;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CheckboxIndeterminateViewIT extends BaseTest {
  private CheckboxIndeterminatePage checkboxPage;

  @BeforeEach
  public void setupCheckboxIndeterminateDemo() {
    checkboxPage = new CheckboxIndeterminatePage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAllCheckboxesAreVisible(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    checkboxPage.getParentCheckbox().assertIsVisible();
    checkboxPage.getChild1Checkbox().assertIsVisible();
    checkboxPage.getChild2Checkbox().assertIsVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testParentIndeterminate(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    checkboxPage.getParentCheckbox().assertIndeterminate(true);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testChild1Unchecked(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    checkboxPage.getChild1Checkbox().assertChecked(false);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testChild2Checked(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    checkboxPage.getChild2Checkbox().assertChecked(true);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testParentCheckboxControlsChildren(SupportedLanguage language) {
    checkboxPage.setRoute(language);

    var parent = checkboxPage.getParentCheckbox();
    var child1 = checkboxPage.getChild1Checkbox();
    var child2 = checkboxPage.getChild2Checkbox();

    // Initially: Child1 unchecked, Child2 checked, Parent indeterminate
    child1.assertChecked(false);
    child2.assertChecked(true);
    parent.assertIndeterminate(true);

    // Check Child1 should make parent checked
    child1.click();
    parent.assertChecked(true);
    parent.assertIndeterminate(false);
    parent.assertChecked(true);

    // Uncheck Child2 should make Parent indeterminate
    child2.click();
    parent.assertIndeterminate(true);

    // Uncheck Child1 should make Parent unchecked
    child1.click();
    parent.assertChecked(false);
    parent.assertIndeterminate(false);
    parent.assertChecked(false);

    // Click parent - should check both children
    parent.click();
    child1.assertChecked(true);
    child2.assertChecked(true);

    // Click parent again - should uncheck both children
    parent.click();
    child1.assertChecked(false);
    child2.assertChecked(false);
  }

}
