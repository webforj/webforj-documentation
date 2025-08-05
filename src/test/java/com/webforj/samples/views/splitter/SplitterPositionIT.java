package com.webforj.samples.views.splitter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.options.BoundingBox;

import com.webforj.samples.pages.splitter.SplitterPositionPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class SplitterPositionIT extends BaseTest {

    private SplitterPositionPage splitterPage;

    @BeforeEach
    public void setupSplitterPosition() {
        navigateToRoute(SplitterPositionPage.getRoute());
        splitterPage = new SplitterPositionPage(page);
    }

    @BrowserTest
    public void testSplitterPosition() {
        BoundingBox masterBox = splitterPage.getPositionedMasterPanel().boundingBox();
        BoundingBox detailBox = splitterPage.getPositionedDetailPanel().boundingBox();

        int masterWidth = (int) masterBox.width;
        int detailWidth = (int) detailBox.width;

        double ratio = (double) masterWidth / detailWidth;

        assertTrue(ratio > 2.5 && ratio < 3.5);
    }
}
