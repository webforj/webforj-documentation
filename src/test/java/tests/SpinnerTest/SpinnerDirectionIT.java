package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerDirectionIT extends BaseTest {

    private SpinnerPage spinnerPage;

    @BeforeEach
    public void setupSpinnerDirection() {
        page.navigate("https://docs.webforj.com/webforj/spinnerdirectiondemo?");
        spinnerPage = new SpinnerPage(page);
    }

    @BrowserTest
    public void testSpinnerDirectionCorrectness() {
        spinnerPage.getClockwiseButton().click();
        assertThat(spinnerPage.getSpinner11()).hasAttribute("clockwise", "");

        spinnerPage.getCounterClockwiseButton().click();
        assertThat(spinnerPage.getSpinner11()).not().hasAttribute("clockwise", "");
    }
} 