package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage.ToolbarProgressBarPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToolbarProgressBarIT extends BaseTest {

    private ToolbarProgressBarPage toolbarProgressBarPage;

    @BeforeEach
    public void setupToolbarProgressBar() {
        navigateToRoute(ToolbarProgressBarPage.getRoute());
        toolbarProgressBarPage = new ToolbarProgressBarPage(page);
    }

    @BrowserTest
    public void testProgressBar() {
        WaitUtil.waitForVisible(toolbarProgressBarPage.getProgressBarToolbar());

        assertThat(toolbarProgressBarPage.getProgressBar()).hasAttribute("animated", "true");

    }
}