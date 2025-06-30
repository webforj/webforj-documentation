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
    public void testLeftAlignment() {
        assertThat(checkBox.getLeftAlignedCheckbox()).hasAttribute("class",
                Pattern.compile(".*bbj-reverse-order.*"));

    }

    @BrowserTest
    public void testLabels() {
        checkBox.getWeeklyCheckboxLabel().click();

        assertThat(checkBox.getWeeklyCheckboxInput()).hasAttribute("aria-checked", "true");

    }
} 