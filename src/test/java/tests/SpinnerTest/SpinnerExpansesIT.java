package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerExpansesIT extends BaseTest {

    private SpinnerPage spinnerPage;

    @BeforeEach
    public void setupSpinnerExpanses() {
        page.navigate("https://docs.webforj.com/webforj/spinnerexpansedemo?");
        spinnerPage = new SpinnerPage(page);
    }

    @BrowserTest
    public void testSpinnerDisplaysCorrectSizesForSmallMediumLarge() {
        assertThat(spinnerPage.getSmallSpinner()).hasAttribute("expanse", "s");
        assertThat(spinnerPage.getMediumSpinner()).hasAttribute("expanse", "m");
        assertThat(spinnerPage.getLargeSpinner()).hasAttribute("expanse", "l");
    }
} 