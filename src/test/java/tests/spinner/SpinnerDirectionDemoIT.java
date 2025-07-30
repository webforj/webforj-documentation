package tests.spinner;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.spinner.SpinnerDirectionDemoPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerDirectionDemoIT extends BaseTest {

    private SpinnerDirectionDemoPage spinnerPage;

    @BeforeEach
    public void setupSpinnerDirection() {
        navigateToRoute(SpinnerDirectionDemoPage.getRoute());
        spinnerPage = new SpinnerDirectionDemoPage(page);
    }

    @BrowserTest
    public void testSpinnerDirectionCorrectness() {
        spinnerPage.getClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).hasAttribute("clockwise", "");

        spinnerPage.getCounterClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).not().hasAttribute("clockwise", "");
    }
}