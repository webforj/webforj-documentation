package com.webforj.samples.views.spinner;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.spinner.SpinnerSpeedDemoPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class SpinnerSpeedDemoIT extends BaseTest {

    private SpinnerSpeedDemoPage spinnerPage;

    @BeforeEach
    public void setupSpinnerSpeeds() {
        navigateToRoute(SpinnerSpeedDemoPage.getRoute());
        spinnerPage = new SpinnerSpeedDemoPage(page);
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
