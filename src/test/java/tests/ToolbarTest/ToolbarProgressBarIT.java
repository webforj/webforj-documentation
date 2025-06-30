package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToolbarProgressBarIT extends BaseTest {

    private ToolbarPage toolbarPage;

    @BeforeEach
    public void setupToolbarProgressBar() {
        page.navigate("https://docs.webforj.com/webforj/toolbarprogressbar?");
        toolbarPage = new ToolbarPage(page);
    }

    @BrowserTest
    public void testProgressBar() {
        WaitUtil.waitForVisible(toolbarPage.getProgressBarToolbar());

        assertThat(toolbarPage.getProgressBar()).hasAttribute("animated", "true");

    }
} 