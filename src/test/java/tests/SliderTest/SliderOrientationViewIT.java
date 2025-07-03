package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage.SliderOrientationPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SliderOrientationViewIT extends BaseTest {

    private SliderOrientationPage sliderPage;

    @BeforeEach
    public void setupSliderOrientation() {
        page.navigate(SliderOrientationPage.getRoute());
        sliderPage = new SliderOrientationPage(page);
    }

    @BrowserTest
    public void testOrientation() {
        assertThat(sliderPage.getOrientationSlider()).hasAttribute("orientation", "vertical");
    }
}