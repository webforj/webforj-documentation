package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SliderTickAndNonTickIT extends BaseTest {

    private SliderPage sliderPage;

    @BeforeEach
    public void setupSliderTickAndNonTick() {
        page.navigate("https://docs.webforj.com/webforj/sliderlabels?");
        sliderPage = new SliderPage(page);
    }

    @BrowserTest
    public void testAlignedThemes() {
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