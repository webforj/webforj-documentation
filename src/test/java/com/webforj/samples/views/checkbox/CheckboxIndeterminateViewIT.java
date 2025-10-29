package com.webforj.samples.views.checkbox;

import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import com.webforj.samples.pages.checkbox.CheckboxIndeterminatePage;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckboxIndeterminateViewIT extends BaseTest {

    private CheckboxIndeterminatePage checkboxIndeterminatePage;

    @BeforeEach
    public void setupCheckboxIndeterminate() {
        navigateToRoute(CheckboxIndeterminatePage.getRoute());
        checkboxIndeterminatePage = new CheckboxIndeterminatePage(page);
    }

    @Test
    public void testParentCheckboxIsIndeterminateWhenChild1AndChild2AreCheckedAndUnchecked() {
        checkboxIndeterminatePage.getChild1Checkbox().check();
        checkboxIndeterminatePage.getChild2Checkbox().check();
        assertThat(checkboxIndeterminatePage.getParentCheckbox()).isChecked();

        checkboxIndeterminatePage.getChild1Checkbox().uncheck();
        checkboxIndeterminatePage.getChild2Checkbox().uncheck();
        assertThat(checkboxIndeterminatePage.getParentCheckbox()).not().isChecked();

    }

    @Test
    public void testIfParentCheckedChildrenAreChecked() {
        checkboxIndeterminatePage.getParentCheckbox().check();
        assertThat(checkboxIndeterminatePage.getChild1Checkbox()).isChecked();
        assertThat(checkboxIndeterminatePage.getChild2Checkbox()).isChecked();
    }

}
