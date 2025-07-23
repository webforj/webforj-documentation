package tests.CheckboxTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.CheckboxPage.CheckboxHorizontalTextPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class CheckboxHorizontalTextIT extends BaseTest {

    private CheckboxHorizontalTextPage checkBox;

    @BeforeEach
    public void setupCheckboxHorizontalText() {
        navigateToRoute(CheckboxHorizontalTextPage.getRoute());
        checkBox = new CheckboxHorizontalTextPage(page);
    }

    @BrowserTest
    public void testLeftAlignmentAndCheckedState() {
        assertThat(checkBox.getLeftAlignedCheckbox()).hasAttribute("class",
                Pattern.compile(".*bbj-reverse-order.*"));

        checkBox.getMonthlyCheckboxInput().click();
        assertThat(checkBox.getMonthlyCheckboxInput()).hasAttribute("aria-checked", "true");

        checkBox.getMonthlyCheckboxInput().click();
        assertThat(checkBox.getMonthlyCheckboxInput()).hasAttribute("aria-checked", "false");

    }
}