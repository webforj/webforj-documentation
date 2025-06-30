package tests.CheckboxTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.CheckboxPage.CheckboxExpansePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class CheckboxExpansesIT extends BaseTest {

    private CheckboxExpansePage checkBox;

    @BeforeEach
    public void setupCheckboxExpanses() {
        navigateToRoute(CheckboxExpansePage.getRoute());
        checkBox = new CheckboxExpansePage(page);
    }

    @BrowserTest
    public void testsetupCheckboxExpanses() {
        assertThat(checkBox.getNoneExpanseCheckbox()).hasAttribute("expanse", "");
        assertThat(checkBox.getXSmallExpanseCheckbox()).hasAttribute("expanse", "xs");
        assertThat(checkBox.getSmallExpanseCheckbox()).hasAttribute("expanse", "s");
        assertThat(checkBox.getMediumExpanseCheckbox()).hasAttribute("expanse", "m");
        assertThat(checkBox.getLargeExpanseCheckbox()).hasAttribute("expanse", "l");
        assertThat(checkBox.getXLargeExpanseCheckbox()).hasAttribute("expanse", "xl");

    }
} 