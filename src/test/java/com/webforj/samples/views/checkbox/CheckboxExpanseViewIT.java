package com.webforj.samples.views.checkbox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.checkbox.CheckboxExpansePage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckboxExpanseViewIT extends BaseTest     {

    private CheckboxExpansePage checkboxExpansePage;

    @BeforeEach
    public void setupCheckboxExpanse() {
        navigateToRoute(CheckboxExpansePage.getRoute());
        checkboxExpansePage = new CheckboxExpansePage(page);
    }

    @Test
    public void testCheckboxXSmallExpanseStillCheckable() {
        checkboxExpansePage.getCheckboxXSmall().check();
        assertThat(checkboxExpansePage.getCheckboxXSmall()).isChecked();
    }

}
