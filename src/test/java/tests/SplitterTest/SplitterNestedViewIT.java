package tests.SplitterTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import pages.SplitterPage.SplitterNestedPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class SplitterNestedViewIT extends BaseTest {

    private SplitterNestedPage splitterPage;

    @BeforeEach
    public void setupSplitterNested() {
        page.navigate(SplitterNestedPage.getRoute());
        splitterPage = new SplitterNestedPage(page);
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