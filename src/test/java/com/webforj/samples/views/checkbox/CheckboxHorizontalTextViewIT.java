package com.webforj.samples.views.checkbox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webforj.samples.pages.checkbox.CheckboxHorizontalTextPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CheckboxHorizontalTextViewIT extends BaseTest {

    private CheckboxHorizontalTextPage checkboxHorizontalTextPage;

    public void setupCheckboxHorizontalText(SupportedLanguage language) {
        navigateToRoute(CheckboxHorizontalTextPage.getRoute(language));
        checkboxHorizontalTextPage = new CheckboxHorizontalTextPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDailyCheckboxIsCheckedAndUncheckedWhenClicked(SupportedLanguage language) {
      setupCheckboxHorizontalText(language);
        assertThat(checkboxHorizontalTextPage.getDailyCheckbox()).isChecked();

        checkboxHorizontalTextPage.getDailyCheckbox().click();
        assertThat(checkboxHorizontalTextPage.getDailyCheckbox()).not().isChecked();
    }
}
