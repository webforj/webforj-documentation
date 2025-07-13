package tests.FieldTest.ColorField;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;

import pages.FieldPages.ColorFieldPage;
import tests.BaseTest;

public class ColorFieldIT extends BaseTest {
    private ColorFieldPage colorFieldPage;

    @BeforeEach
    public void setupColorField() {
        navigateToRoute(ColorFieldPage.getRoute());
        colorFieldPage = new ColorFieldPage(page);
    }

    @Test
    public void testTetradicComplementaryColorBlocks() {
        assertThat(colorFieldPage.getColorBlocks()).hasCount(4);

        String[] expectedColors = {
                "rgb(255, 0, 0)",
                "rgb(255, 255, 0)",
                "rgb(0, 255, 0)",
                "rgb(0, 255, 255)"
        };

        for (int i = 0; i < colorFieldPage.getColorBlocks().count(); i++) {
            Locator block = colorFieldPage.getColorBlocks().nth(i);

            String styleAttr = block.getAttribute("style");
            assertTrue(styleAttr.contains(expectedColors[i]));

            assertThat(block).isVisible();
        }
    }
}