package com.webforj.samples.views.checkbox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.webforj.samples.views.BaseTest;

public class CheckboxHorizontalTextViewIT extends BaseTest {

    @BeforeEach
    public void setupCheckboxHorizontalText() {
        navigateToRoute("checkboxhorizontaltext");
    }

    @Test
    public void testDailyCheckboxIsCheckedAndUncheckedWhenClicked() {
        Locator checkbox = page.getByText("Daily").nth(1);

        assertThat(checkbox).isChecked();

        checkbox.click();
        assertThat(checkbox).not().isChecked();
    }

}
