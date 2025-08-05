package com.webforj.samples.views.splitter;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator.DragToOptions;

import com.webforj.samples.pages.splitter.SplitterOrientationPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class SplitterOrientationIT extends BaseTest {

    private SplitterOrientationPage splitterPage;

    @BeforeEach
    public void setupSplitterOrientation() {
        navigateToRoute(SplitterOrientationPage.getRoute());
        splitterPage = new SplitterOrientationPage(page);
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
