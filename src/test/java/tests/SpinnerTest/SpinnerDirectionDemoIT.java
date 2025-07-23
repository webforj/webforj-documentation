package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage.SpinnerDirectionPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerDirectionDemoIT extends BaseTest {

    private SpinnerDirectionPage spinnerPage;

    @BeforeEach
    public void setupSpinnerDirection() {
        navigateToRoute(SpinnerDirectionPage.getRoute());
        spinnerPage = new SpinnerDirectionPage(page);
    }

    @BrowserTest
    public void testSpinnerDirectionCorrectness() {
        spinnerPage.getClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).hasAttribute("clockwise", "");

        spinnerPage.getCounterClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).not().hasAttribute("clockwise", "");
    }
}