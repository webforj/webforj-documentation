package com.webforj.samples.views.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.slider.SliderPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class SliderIT extends BaseTest {

    private SliderPage sliderPage;

    @BeforeEach
    public void setupSliderView() {
        navigateToRoute(SliderPage.getRoute());
        sliderPage = new SliderPage(page);
    }

    @BrowserTest
    public void testSliderView() {
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "50.0");

        sliderPage.getMuteIcon().click();
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "0.0");

        sliderPage.getUnmuteIcon().click();
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "100.0");
    }
}
