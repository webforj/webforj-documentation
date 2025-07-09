package tests.SplitterTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import pages.SplitterPage.SplitterMaxMinPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SplitterMaxMinViewIT extends BaseTest {

    private SplitterMaxMinPage splitterPage;

    @BeforeEach
    public void setupSplitterMaxMin() {
        navigateToRoute(SplitterMaxMinPage.getRoute());
        splitterPage = new SplitterMaxMinPage(page);
    }

    @BrowserTest
    public void testSplitterMaxMin() {
        String masterPanelStyle = splitterPage.getMasterPanelWithConstraints().getAttribute("style");

        page.waitForTimeout(1000);

        assertTrue(masterPanelStyle.contains("75%") && masterPanelStyle.contains("200px"));
    }
}