package tests.checkbox;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.checkbox.CheckboxIndeterminatePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class CheckboxIndeterminateIT extends BaseTest {

    private CheckboxIndeterminatePage checkBox;

    @BeforeEach
    public void setupCheckboxIndeterminate() {
        navigateToRoute(CheckboxIndeterminatePage.getRoute());
        checkBox = new CheckboxIndeterminatePage(page);
    }

    @BrowserTest
    public void testParentCheckbox() {
        assertThat(checkBox.getParentCheckbox()).hasAttribute("indeterminate", "");

        checkBox.getChild1CheckboxInput().click();
        assertThat(checkBox.getParentCheckbox()).hasAttribute("checked", "");

        checkBox.getChild1CheckboxInput().click();
        checkBox.getChild2CheckboxInput().click();

        assertThat(checkBox.getParentCheckbox()).not().hasAttribute("checked", "");
        assertThat(checkBox.getParentCheckbox()).not().hasAttribute("indeterminate", "");
    }
}