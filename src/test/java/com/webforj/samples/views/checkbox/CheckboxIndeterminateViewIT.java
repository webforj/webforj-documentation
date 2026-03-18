package com.webforj.samples.views.checkbox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.LocatorAssertions;
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
    assertThat(checkboxPage.getParentCheckbox()).isVisible();
    assertThat(checkboxPage.getChild1Checkbox()).isVisible();
    assertThat(checkboxPage.getChild2Checkbox()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testParentIndeterminate(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    assertThat(checkboxPage.getParentCheckbox()).isChecked(new LocatorAssertions.IsCheckedOptions().setIndeterminate(true));
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testChild1Unchecked(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    assertThat(checkboxPage.getChild1Checkbox()).not().isChecked();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testChild2Checked(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    assertThat(checkboxPage.getChild2Checkbox()).isChecked();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testParentCheckboxControlsChildren(SupportedLanguage language) {
    checkboxPage.setRoute(language);

    var parent = checkboxPage.getParentCheckbox();
    var child1 = checkboxPage.getChild1Checkbox();
    var child2 = checkboxPage.getChild2Checkbox();

    // Initially: Child1 unchecked, Child2 checked, Parent indeterminate
    assertThat(child1).not().isChecked();
    assertThat(child2).isChecked();
    assertThat(parent).isChecked(new LocatorAssertions.IsCheckedOptions().setIndeterminate(true));

    // Check Child1 should make parent checked
    child1.click();
    assertThat(parent).isChecked();

    // Uncheck Child2 should make Parent indeterminate
    child2.click();
    assertThat(parent).isChecked(new LocatorAssertions.IsCheckedOptions().setIndeterminate(true));

    // Uncheck Child1 should make Parent unchecked
    child1.click();
    assertThat(parent).not().isChecked();

    // Click parent - should check both children
    parent.click();
    assertThat(child1).isChecked();
    assertThat(child2).isChecked();

    // Click parent again - should uncheck both children
    parent.click();
    assertThat(child1).not().isChecked();
    assertThat(child2).not().isChecked();
  }

}
