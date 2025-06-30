package tests.FieldTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.FieldPages.ColorFieldPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ColorFieldIT extends BaseTest {
    private ColorFieldPage colorFieldPage;

    @BeforeEach
    public void setupColorField() {
        page.navigate("https://docs.webforj.com/webforj/colorfield?");
        colorFieldPage = new ColorFieldPage(page);
    }

    @BrowserTest
    public void testTetradicComplementaryColorBlocks() {
        WaitUtil.waitForVisible(colorFieldPage.getColorField());
        
        assertEquals(4, colorFieldPage.getColorBlocks().count());

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