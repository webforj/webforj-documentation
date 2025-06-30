package tests.SplitterTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import pages.SplitterPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class SplitterNestedIT extends BaseTest {

    private SplitterPage splitterPage;

    @BeforeEach
    public void setupSplitterNested() {
        page.navigate("https://docs.webforj.com/webforj/splitternested?");
        splitterPage = new SplitterPage(page);
    }

    @BrowserTest
    public void testSplitterNested() {
        WaitUtil.waitForVisible(splitterPage.getSplitter());
        WaitUtil.waitForVisible(splitterPage.getNestedSplitter());

        String splitterOrinetation = splitterPage.getSplitter().getAttribute("orientation");
        String nestedSplitterOrinetation = splitterPage.getNestedSplitter().getAttribute("orientation");

        assertEquals(splitterOrinetation, "horizontal");
        assertEquals(nestedSplitterOrinetation, "vertical");
    }
} 