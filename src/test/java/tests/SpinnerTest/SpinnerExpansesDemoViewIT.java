package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage.SpinnerExpansesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerExpansesDemoViewIT extends BaseTest {

    private SpinnerExpansesPage spinnerPage;

    @BeforeEach
    public void setupSpinnerExpanses() {
        page.navigate(SpinnerExpansesPage.getRoute());
        spinnerPage = new SpinnerExpansesPage(page);
    }

    @BrowserTest
    public void testSpinnerDisplaysCorrectSizesForSmallMediumLarge() {
        assertThat(spinnerPage.getSmallSpinner()).hasAttribute("expanse", "s");
        assertThat(spinnerPage.getMediumSpinner()).hasAttribute("expanse", "m");
        assertThat(spinnerPage.getLargeSpinner()).hasAttribute("expanse", "l");
    }
}