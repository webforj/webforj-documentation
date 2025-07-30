package tests.splitter;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator.DragToOptions;

import pages.splitter.SplitterBasicPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SplitterBasicIT extends BaseTest {

    private SplitterBasicPage splitterPage;

    @BeforeEach
    public void setupSplitterBasics() {
        navigateToRoute(SplitterBasicPage.getRoute());
        splitterPage = new SplitterBasicPage(page);
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