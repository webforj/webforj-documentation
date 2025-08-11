// package com.webforj.samples.views.fields.colorfield;

// import static org.junit.jupiter.api.Assumptions.assumeTrue;
// import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.webforj.samples.pages.fields.colorfield.ColorFieldPage;
// import com.webforj.samples.views.BaseTest;

// public class ColorFieldIT extends BaseTest {
//     private ColorFieldPage colorFieldPage;

//     @BeforeEach
//     public void setupColorField() {
//         navigateToRoute(ColorFieldPage.getRoute());
//         colorFieldPage = new ColorFieldPage(page);
//     }

//     @Test
//     public void testTetradicComplementaryColorBlocks() {
//         assumeTrue(!browserType.equalsIgnoreCase("webkit"), "Skipping on WebKit");
//         assertThat(colorFieldPage.getColorBlocks()).hasCount(4);

//         String[] expectedColors = {
//                 "rgb(255, 0, 0)",
//                 "rgb(255, 255, 0)",
//                 "rgb(0, 255, 0)",
//                 "rgb(0, 255, 255)"
//         };

//         for (int i = 0; i < colorFieldPage.getColorBlocks().count(); i++) {
//             assertThat(colorFieldPage.getColorBlocks().nth(i))
//                     .hasCSS("background-color", expectedColors[i]);
//         }
//     }
// }
