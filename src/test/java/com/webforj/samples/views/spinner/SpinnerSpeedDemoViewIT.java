package com.webforj.samples.views.spinner;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.spinner.SpinnerSpeedDemoPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SpinnerSpeedDemoViewIT extends BaseTest {

    private SpinnerSpeedDemoPage spinnerPage;

    public void setupSpinnerSpeeds(SupportedLanguage language) {
        navigateToRoute(SpinnerSpeedDemoPage.getRoute(language));
        spinnerPage = new SpinnerSpeedDemoPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSpinnerSpeedIsSet(SupportedLanguage language) {
        setupSpinnerSpeeds(language);

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
