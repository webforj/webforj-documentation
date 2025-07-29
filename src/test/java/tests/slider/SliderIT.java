package tests.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.slider.SliderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

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