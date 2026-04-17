package com.webforj.samples.views.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.slider.SliderLabelsPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SliderLabelsViewIT extends BaseTest {

    private SliderLabelsPage sliderPage;

    public void setupSliderLabels(SupportedLanguage language) {
        navigateToRoute(SliderLabelsPage.getRoute(language));
        sliderPage = new SliderLabelsPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testLabelsThemesSlider(SupportedLanguage language) {
        setupSliderLabels(language);
        sliderPage.getTenDegreeOption().click();
        assertThat(sliderPage.getSliderLabel()).hasAttribute("theme", "primary");

        sliderPage.getFortyDegreeOption().click();
        assertThat(sliderPage.getSliderLabel()).hasAttribute("theme", "success");
    }
}
