package com.webforj.samples.views.checkbox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webforj.samples.pages.checkbox.CheckboxHorizontalTextPage;
import com.webforj.samples.views.BaseTest;

public class CheckboxHorizontalTextViewIT extends BaseTest {

    private CheckboxHorizontalTextPage checkboxHorizontalTextPage;

    @BeforeEach
    public void setupCheckboxHorizontalText() {
        navigateToRoute(CheckboxHorizontalTextPage.getRoute());
        checkboxHorizontalTextPage = new CheckboxHorizontalTextPage(page);
    }

    @Test
    public void testDailyCheckboxIsCheckedAndUncheckedWhenClicked() {
        assertThat(checkboxHorizontalTextPage.getDailyCheckbox()).isChecked();

        checkboxHorizontalTextPage.getDailyCheckbox().click();
        assertThat(checkboxHorizontalTextPage.getDailyCheckbox()).not().isChecked();
    }
}
