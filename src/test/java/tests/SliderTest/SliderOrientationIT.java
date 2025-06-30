package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SliderOrientationIT extends BaseTest {

    private SliderPage sliderPage;

    @BeforeEach
    public void setupSliderOrientation() {
        page.navigate("https://docs.webforj.com/webforj/sliderorientation?");
        sliderPage = new SliderPage(page);
    }

    @BrowserTest
    public void testOrientation() {
        assertThat(sliderPage.getOrientationSlider()).hasAttribute("orientation", "vertical");
    }
} 