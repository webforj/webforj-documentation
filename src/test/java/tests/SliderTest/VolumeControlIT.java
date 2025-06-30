package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class VolumeControlIT extends BaseTest {

    private SliderPage sliderPage;

    @BeforeEach
    public void setupVolumeControl() {
        page.navigate("https://docs.webforj.com/webforj/slider?");
        sliderPage = new SliderPage(page);
    }

    @BrowserTest
    public void testMuteAndUnmuteIcons() {
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "50.0");

        sliderPage.getMuteIcon().click();
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "0.0");

        sliderPage.getUnmuteIcon().click();
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "100.0");
    }
} 