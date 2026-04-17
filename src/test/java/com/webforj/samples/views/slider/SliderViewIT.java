package com.webforj.samples.views.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.slider.SliderPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SliderViewIT extends BaseTest {

    private SliderPage sliderPage;

    public void setupSliderView(SupportedLanguage language) {
        navigateToRoute(SliderPage.getRoute(language));
        sliderPage = new SliderPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testVolumeSlider(SupportedLanguage language) {
      setupSliderView(language);
        sliderPage.getVolumeOffButton().click();
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "0.0");

        sliderPage.getVolumeOnButton().click();
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "100.0");
    }
}
