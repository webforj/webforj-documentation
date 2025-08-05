package com.webforj.samples.views.splitter;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator.DragToOptions;

import com.webforj.samples.pages.splitter.SplitterBasicPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

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
