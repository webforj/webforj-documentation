// package com.webforj.samples.views.slider;

// import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

// import java.util.regex.Pattern;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.webforj.samples.pages.slider.SliderTickSpacingPage;
// import com.webforj.samples.views.BaseTest;

// public class SliderTickSpacingIT extends BaseTest {

//     private SliderTickSpacingPage sliderPage;

//     @BeforeEach
//     public void setupMajorandMinorTick() {
//         navigateToRoute(SliderTickSpacingPage.getRoute());
//         sliderPage = new SliderTickSpacingPage(page);
//     }

//     @Test
//     public void testMajorandMinorTick() {
//         assertThat(sliderPage.getMajorTickInput()).hasValue("20");
//         assertThat(sliderPage.getMinorTickInput()).hasValue("10");

//         // 20 major tick causes 6 major ticks.
//         // 10 minor tick causes 11 minor ticks.

//         assertThat(sliderPage.getMajorTicks()).hasCount(6);
//         assertThat(sliderPage.getMinorTicks()).hasCount(11);
//     }

//     @Test
//     public void testHiddingTicks() {
//         sliderPage.getTickToggle().click();
//         assertThat(sliderPage.getTickSpacingControl())
//                 .hasClass(Pattern.compile(".*control--noTicks.*"));
//     }

//     @Test
//     public void testSnappingTicks() {
//         sliderPage.getSnapToggle().click();
//         assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "50.0");

//         sliderPage.getSnapThumb().click();
//         page.keyboard().press("ArrowRight");
//         assertThat(sliderPage.getLowerHandle()).hasAttribute("aria-valuenow", "60.0");
//     }
// }
