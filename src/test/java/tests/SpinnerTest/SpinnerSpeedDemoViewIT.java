package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage.SpinnerSpeedsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerSpeedDemoViewIT extends BaseTest {

    private SpinnerSpeedsPage spinnerPage;

    @BeforeEach
    public void setupSpinnerSpeeds() {
        page.navigate(SpinnerSpeedsPage.getRoute());
        spinnerPage = new SpinnerSpeedsPage(page);
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