package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage.SliderViewPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SliderViewIT extends BaseTest {

    private SliderViewPage sliderPage;

    @BeforeEach
    public void setupSliderView() {
        navigateToRoute(SliderViewPage.getRoute());
        sliderPage = new SliderViewPage(page);
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