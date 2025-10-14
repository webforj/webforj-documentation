package com.webforj.samples.views.spinner;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.spinner.SpinnerDirectionDemoPage;
import com.webforj.samples.views.BaseTest;

public class SpinnerDirectionDemoViewIT extends BaseTest {

    private SpinnerDirectionDemoPage spinnerPage;

    @BeforeEach
    public void setupSpinnerDirection() {
        navigateToRoute(SpinnerDirectionDemoPage.getRoute());
        spinnerPage = new SpinnerDirectionDemoPage(page);
    }

    @Test
    public void testSpinnerDirection() {
        spinnerPage.getClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).hasAttribute("clockwise", "");

        spinnerPage.getCounterClockwiseButton().click();
        assertThat(spinnerPage.getSpinner()).not().hasAttribute("clockwise", "");
    }
}
