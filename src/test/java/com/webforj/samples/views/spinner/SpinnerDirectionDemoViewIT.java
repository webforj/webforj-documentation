package com.webforj.samples.views.spinner;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.spinner.SpinnerDirectionDemoPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SpinnerDirectionDemoViewIT extends BaseTest {

    private SpinnerDirectionDemoPage spinnerPage;

    public void setupSpinnerDirection(SupportedLanguage language) {
        navigateToRoute(SpinnerDirectionDemoPage.getRoute(language));
        spinnerPage = new SpinnerDirectionDemoPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSpinnerDirectionIsClockwise(SupportedLanguage language) {
        setupSpinnerDirection(language);
        spinnerPage.getClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).hasAttribute("clockwise", "");

        spinnerPage.getCounterClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).not().hasAttribute("clockwise", "");
    }
}
