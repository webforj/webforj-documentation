package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage.SliderThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SliderThemesIT extends BaseTest {

    private SliderThemesPage sliderPage;

    @BeforeEach
    public void setupSliderThemes() {
        page.navigate(SliderThemesPage.getRoute());
        sliderPage = new SliderThemesPage(page);
    }

    @BrowserTest
    public void testSliderThemes() {
        assertThat(sliderPage.getDefaultThemeSlider()).hasAttribute("theme", "default");
        assertThat(sliderPage.getDangerThemeSlider()).hasAttribute("theme", "danger");
        assertThat(sliderPage.getGrayThemeSlider()).hasAttribute("theme", "gray");
        assertThat(sliderPage.getInfoThemeSlider()).hasAttribute("theme", "info");
        assertThat(sliderPage.getSuccessThemeSlider()).hasAttribute("theme", "success");
        assertThat(sliderPage.getWarningThemeSlider()).hasAttribute("theme", "warning");
    }
}