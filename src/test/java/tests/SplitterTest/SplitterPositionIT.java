package tests.SplitterTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.options.BoundingBox;

import pages.SplitterPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SplitterPositionIT extends BaseTest {

    private SplitterPage splitterPage;

    @BeforeEach
    public void setupSplitterPosition() {
        page.navigate("https://docs.webforj.com/webforj/splitterposition?");
        splitterPage = new SplitterPage(page);
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