package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage.SpinnerSpeedPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerSpeedDemoIT extends BaseTest {

    private SpinnerSpeedPage spinnerPage;

    @BeforeEach
    public void setupSpinnerSpeeds() {
        navigateToRoute(SpinnerSpeedPage.getRoute());
        spinnerPage = new SpinnerSpeedPage(page);
    }

    @BrowserTest
    public void testPauseAndResumeFunctionality() {
        assertThat(spinnerPage.getSpinner11())
                .hasAttribute("style", "--_dwc-spinner-speed: 1000ms;");

        spinnerPage.getPauseButton().click();
        assertThat(spinnerPage.getSpinner11()).hasAttribute("paused", "");

        spinnerPage.getFastButton().click();
        assertThat(spinnerPage.getSpinner11())
                .hasAttribute("style", "--_dwc-spinner-speed: 200ms;");

        spinnerPage.getMediumButton().click();
        assertThat(spinnerPage.getSpinner11())
                .hasAttribute("style", "--_dwc-spinner-speed: 500ms;");

        spinnerPage.getSlowButton().click();
        assertThat(spinnerPage.getSpinner11())
                .hasAttribute("style", "--_dwc-spinner-speed: 1000ms;");
    }
}