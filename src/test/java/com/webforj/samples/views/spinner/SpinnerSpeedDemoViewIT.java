package com.webforj.samples.views.spinner;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.spinner.SpinnerSpeedDemoPage;
import com.webforj.samples.views.BaseTest;

public class SpinnerSpeedDemoViewIT extends BaseTest {

    private SpinnerSpeedDemoPage spinnerPage;

    @BeforeEach
    public void setupSpinnerSpeeds() {
        navigateToRoute(SpinnerSpeedDemoPage.getRoute());
        spinnerPage = new SpinnerSpeedDemoPage(page);
    }

    @Test
    public void testPauseAndResumeFunctionality() {

        spinnerPage.getPauseButton().click();
        assertThat(spinnerPage.getSpinner()).hasAttribute("paused", "");

        spinnerPage.getFastButton().click();
        assertThat(spinnerPage.getSpinner())
                .hasAttribute("style", "--_dwc-spinner-speed: 200ms;");

        spinnerPage.getMediumButton().click();
        assertThat(spinnerPage.getSpinner())
                .hasAttribute("style", "--_dwc-spinner-speed: 500ms;");

        spinnerPage.getSlowButton().click();
        assertThat(spinnerPage.getSpinner())
                .hasAttribute("style", "--_dwc-spinner-speed: 1000ms;");
    }
}
