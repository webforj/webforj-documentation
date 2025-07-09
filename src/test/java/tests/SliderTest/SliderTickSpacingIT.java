package tests.SliderTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.SliderPage.SliderTickSpacingPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SliderTickSpacingIT extends BaseTest {

    private SliderTickSpacingPage sliderPage;

    @BeforeEach
    public void setupMajorandMinorTick() {
        navigateToRoute(SliderTickSpacingPage.getRoute());
        sliderPage = new SliderTickSpacingPage(page);
    }

    @BrowserTest
    public void testMajorandMinorTick() {
        assertThat(sliderPage.getMajorTickInput()).hasValue("20");
        assertThat(sliderPage.getMinorTickInput()).hasValue("10");

        // 20 major tick causes 6 major ticks.
        // 10 minor tick causes 11 minor ticks.

        assertThat(sliderPage.getMajorTicks()).hasCount(6);
        assertThat(sliderPage.getMinorTicks()).hasCount(11);
    }

    @BrowserTest
    public void testHiddingTicks() {
        sliderPage.getTickToggle().click();
        assertThat(sliderPage.getTickSpacingControl())
                .hasClass(Pattern.compile(".*control--noTicks.*"));
    }

    @BrowserTest
    public void testSnappingTicks() {
        sliderPage.getSnapToggle().click();
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "50.0");

        sliderPage.getSnapThumb().click();
        page.keyboard().press("ArrowRight");
        assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "60.0");
    }
}