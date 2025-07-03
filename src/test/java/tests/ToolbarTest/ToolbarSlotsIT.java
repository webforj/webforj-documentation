package tests.ToolbarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToolbarPage.ToolbarSlotsPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToolbarSlotsIT extends BaseTest {

    private ToolbarSlotsPage toolbarSlotsPage;

    @BeforeEach
    public void setupToolbarSlots() {
        navigateToRoute(ToolbarSlotsPage.getRoute());
        toolbarSlotsPage = new ToolbarSlotsPage(page);
    }

    @BrowserTest
    public void testUIElemets() {
        WaitUtil.waitForVisible(toolbarSlotsPage.getSlotsToolbar());

        assertThat(toolbarSlotsPage.getSlotsTablerIcon()).isVisible();
        assertThat(toolbarSlotsPage.getSlotsApplicationTitle()).isVisible();
        assertThat(toolbarSlotsPage.getToolbarTitle()).isVisible();
        assertThat(toolbarSlotsPage.getSettingsButton()).isVisible();
        assertThat(toolbarSlotsPage.getProfileButton()).isVisible();
        assertThat(toolbarSlotsPage.getSlotsMainTitle()).isVisible();
        assertThat(toolbarSlotsPage.getSlotsParagraph()).isVisible();

    }
}