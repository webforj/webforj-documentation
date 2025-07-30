package tests.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.slider.SliderLabelsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

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