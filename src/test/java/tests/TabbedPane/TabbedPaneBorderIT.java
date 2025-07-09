package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TabbedPanePage.TabbedPaneBorderPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class TabbedPaneBorderIT extends BaseTest {

    private TabbedPaneBorderPage tabbedPaneBorderPage;

    @BeforeEach
    public void setupBorder() {
        navigateToRoute(TabbedPaneBorderPage.getRoute());
        tabbedPaneBorderPage = new TabbedPaneBorderPage(page);
    }

    @BrowserTest
    public void testBorder() {
        tabbedPaneBorderPage.getHideBorderToggle().click();
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("borderless", "");
        tabbedPaneBorderPage.getBorderOrdersTab().click();
        assertThat(tabbedPaneBorderPage.getBorderOrdersTab()).hasAttribute("active", "");

        tabbedPaneBorderPage.getHideActiveIndicatorToggle().click();
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("hide-active-indicator", "");
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("borderless", "");
        tabbedPaneBorderPage.getBorderDashboardTab().click();
        assertThat(tabbedPaneBorderPage.getBorderDashboardTab()).hasAttribute("active", "");

        tabbedPaneBorderPage.getHideBorderToggle().click();
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("hide-active-indicator", "");
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).not().hasAttribute("borderless", "");
        tabbedPaneBorderPage.getBorderOrdersTab().click();
        assertThat(tabbedPaneBorderPage.getBorderOrdersTab()).hasAttribute("active", "");

        tabbedPaneBorderPage.getHideActiveIndicatorToggle().click();
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).not().hasAttribute("hide-active-indicator", "");
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).not().hasAttribute("borderless", "");
        tabbedPaneBorderPage.getBorderDashboardTab().click();
        assertThat(tabbedPaneBorderPage.getBorderDashboardTab()).hasAttribute("active", "");
    }
}