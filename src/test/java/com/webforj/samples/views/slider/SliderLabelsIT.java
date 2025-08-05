package com.webforj.samples.views.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.slider.SliderLabelsPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class SliderLabelsIT extends BaseTest {

    private SliderLabelsPage sliderPage;

    @BeforeEach
    public void setupSliderLabels() {
        navigateToRoute(SliderLabelsPage.getRoute());
        sliderPage = new SliderLabelsPage(page);
    }

    @BrowserTest
    public void testLabelsThemes() {
        sliderPage.getTenDegreeOption().click();
        assertThat(sliderPage.getLabelsSlider()).hasAttribute("theme", "primary");

        sliderPage.getFortyDegreeOption().click();
        assertThat(sliderPage.getLabelsSlider()).hasAttribute("theme", "success");

        sliderPage.getSixtyDegreeOption().click();
        assertThat(sliderPage.getLabelsSlider()).hasAttribute("theme", "warning");

        sliderPage.getNinetyDegreeOption().click();
        assertThat(sliderPage.getLabelsSlider()).hasAttribute("theme", "danger");
    }
}
