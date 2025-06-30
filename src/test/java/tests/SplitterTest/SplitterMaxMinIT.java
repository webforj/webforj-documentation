package tests.SplitterTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import pages.SplitterPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class SplitterMaxMinIT extends BaseTest {

    private SplitterPage splitterPage;

    @BeforeEach
    public void setupSplitterMaxMin() {
        page.navigate("https://docs.webforj.com/webforj/splitterminmax?");
        splitterPage = new SplitterPage(page);
    }

    @BrowserTest
    public void testSplitterMaxMin() {
        String masterPanelStyle = splitterPage.getMasterPanelWithConstraints().getAttribute("style");

        page.waitForTimeout(1000);

        assertTrue(masterPanelStyle.contains("75%") && masterPanelStyle.contains("200px"));
    }
} 