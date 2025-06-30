package tests.CheckboxTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.CheckboxPage.CheckboxIndeterminatePage;
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
    public void testIndeterminateState() {
        assertThat(checkBox.getChild2CheckboxInput()).hasAttribute("aria-checked", "true");
        assertThat(checkBox.getChild1CheckboxInput()).hasAttribute("aria-checked", "false");
        assertThat(checkBox.getParentCheckbox()).hasAttribute("indeterminate", "");
    }

    @BrowserTest
    public void testParentCheckbox() {
        checkBox.getChild1CheckboxInput().click();

        assertThat(checkBox.getParentCheckbox()).hasAttribute("checked", "");
    }

    @BrowserTest
    public void testDeselectingAllChildren() {
        checkBox.getChild1CheckboxInput().click();

        assertThat(checkBox.getParentCheckbox()).hasAttribute("checked", "");

        checkBox.getChild1CheckboxInput().click();
        checkBox.getChild2CheckboxInput().click();

        assertThat(checkBox.getParentCheckbox()).not().hasAttribute("checked", "");
        assertThat(checkBox.getParentCheckbox()).not().hasAttribute("indeterminate", "");

    }
} 