package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToolbarSlotsIT extends BaseTest {

    private ToolbarPage toolbarPage;

    @BeforeEach
    public void setupToolbarSlots() {
        page.navigate("https://docs.webforj.com/webforj/toolbarslots?");
        toolbarPage = new ToolbarPage(page);
    }

    @BrowserTest
    public void testUIElemets() {
        WaitUtil.waitForVisible(toolbarPage.getSlotsToolbar());

        assertThat(toolbarPage.getSlotsTablerIcon()).isVisible();
        assertThat(toolbarPage.getSlotsApplicationTitle()).isVisible();
        assertThat(toolbarPage.getToolbarTitle()).isVisible();
        assertThat(toolbarPage.getSettingsButton()).isVisible();
        assertThat(toolbarPage.getProfileButton()).isVisible();
        assertThat(toolbarPage.getSlotsMainTitle()).isVisible();
        assertThat(toolbarPage.getSlotsParagraph()).isVisible();

    }
} 