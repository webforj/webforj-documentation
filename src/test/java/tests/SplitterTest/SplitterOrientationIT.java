package tests.SplitterTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator.DragToOptions;

import pages.SplitterPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SplitterOrientationIT extends BaseTest {

    private SplitterPage splitterPage;

    @BeforeEach
    public void setupSplitterOrientation() {
        page.navigate("https://docs.webforj.com/webforj/splitterorientation?");
        splitterPage = new SplitterPage(page);
    }

    @BrowserTest
    public void testSplitterOrientation() {
        String initialStyle = splitterPage.getMasterPanel().getAttribute("style");

        splitterPage.getSplitterIcon().dragTo(splitterPage.getSplitterIcon(), new DragToOptions()
                .setTargetPosition(splitterPage.getSplitterIcon().boundingBox().x, splitterPage.getSplitterIcon().boundingBox().y + 50)
                .setForce(true));

        page.waitForTimeout(1000);

        String finalStyle = splitterPage.getMasterPanel().getAttribute("style");

        assertNotEquals(finalStyle, initialStyle);
    }
} 