package tests.SplitterTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator.DragToOptions;

import pages.SplitterPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SplitterBasicsIT extends BaseTest {

    private SplitterPage splitterPage;

    @BeforeEach
    public void setupSplitterBasics() {
        page.navigate("https://docs.webforj.com/webforj/splitterbasic?");
        splitterPage = new SplitterPage(page);
    }

    @BrowserTest
    public void testSplitterBasics() {
        String initialStyle = splitterPage.getMasterPanel().getAttribute("style");

        splitterPage.getSplitterIcon().dragTo(splitterPage.getSplitterIcon(), new DragToOptions()
                .setTargetPosition(splitterPage.getSplitterIcon().boundingBox().x + 50, splitterPage.getSplitterIcon().boundingBox().y)
                .setForce(true));

        page.waitForTimeout(1000);

        String finalStyle = splitterPage.getMasterPanel().getAttribute("style");

        assertNotEquals(finalStyle, initialStyle);
    }
} 