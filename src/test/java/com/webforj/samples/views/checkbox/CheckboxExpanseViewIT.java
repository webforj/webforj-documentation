package com.webforj.samples.views.checkbox;

import com.webforj.component.Expanse;
import com.webforj.samples.pages.checkbox.CheckboxExpansePage;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CheckboxExpanseViewIT extends BaseTest {
  private CheckboxExpansePage checkboxPage;

  @BeforeEach
  public void setupCheckboxExpanseDemo() {
    checkboxPage = new CheckboxExpansePage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAllCheckboxesHaveCorrectExpanse(SupportedLanguage language) {
    checkboxPage.setRoute(language);
    var expanses = Expanse.values();
    for (int i = expanses.length - 1, j = 0; i >= 0; i--, j++) {
      var checkbox = checkboxPage.getCheckbox(i);
      checkbox.assertExpanse(expanses[j]);
    }
  }

}
