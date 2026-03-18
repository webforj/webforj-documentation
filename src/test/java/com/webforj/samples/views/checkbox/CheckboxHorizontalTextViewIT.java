package com.webforj.samples.views.checkbox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.webforj.samples.utils.WebforjAssertions.*;

import com.webforj.concern.HasHorizontalAlignment;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.checkbox.CheckboxHorizontalTextPage;
import com.webforj.samples.utils.WebforjAssertions;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CheckboxHorizontalTextViewIT extends BaseTest {
  private CheckboxHorizontalTextPage checkboxPage;

  @BeforeEach
  public void setupCheckboxHorizontalText() {
    checkboxPage = new CheckboxHorizontalTextPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testDailyCheckboxIsChecked(SupportedLanguage language) {
    checkboxPage.setRoute(language);

    // Second column checkboxes have bbj-reverse-order (right-aligned)
    var rightCheckbox = checkboxPage.getSecondColumnCheckbox("Daily");
    rightCheckbox.assertThat().isChecked();
    rightCheckbox.assertThat().hasHorizontalAlignment(HasHorizontalAlignment.Alignment.RIGHT);

    // First column checkboxes have no bbj-reverse-order (left-aligned)
    var leftCheckbox = checkboxPage.getFirstColumnCheckbox("Daily");
    leftCheckbox.assertThat().isChecked();
    leftCheckbox.assertThat().hasHorizontalAlignment(HasHorizontalAlignment.Alignment.LEFT);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testOtherCheckboxesAreUncheckedWithCorrectAlignment(SupportedLanguage language) {
    checkboxPage.setRoute(language);

    // Test that other checkboxes are unchecked but have correct alignment
    for (String name : checkboxPage.getNames()) {
      // Second column checkboxes have bbj-reverse-order (right-aligned)
      var rightCheckbox = checkboxPage.getSecondColumnCheckbox(name);
      rightCheckbox.assertThat().not().isChecked();
      rightCheckbox.assertThat().hasHorizontalAlignment(HasHorizontalAlignment.Alignment.RIGHT);

      // First column checkboxes have no bbj-reverse-order (left-aligned)
      var leftCheckbox = checkboxPage.getFirstColumnCheckbox(name);
      leftCheckbox.assertThat().not().isChecked();
      leftCheckbox.assertThat().hasHorizontalAlignment(HasHorizontalAlignment.Alignment.LEFT);
    }
  }
}
