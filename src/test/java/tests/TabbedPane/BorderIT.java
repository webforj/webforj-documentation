package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TabbedPanePage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class BorderIT extends BaseTest {

    private TabbedPanePage tabbedPanePage;

    @BeforeEach
    public void setupBorder() {
        page.navigate("https://docs.webforj.com/webforj/tabbedpaneborder?");
        tabbedPanePage = new TabbedPanePage(page);
    }

    @BrowserTest
    public void testBorder() {
        WaitUtil.waitForVisible(tabbedPanePage.getBorderTabbedPane());

        tabbedPanePage.getHideBorderToggle().click();
        assertThat(tabbedPanePage.getBorderTabbedPane()).hasAttribute("borderless", "");
        tabbedPanePage.getBorderOrdersTab().click();
        assertThat(tabbedPanePage.getBorderOrdersTab()).hasAttribute("active", "");

        tabbedPanePage.getHideActiveIndicatorToggle().click();
        assertThat(tabbedPanePage.getBorderTabbedPane()).hasAttribute("hide-active-indicator", "");
        assertThat(tabbedPanePage.getBorderTabbedPane()).hasAttribute("borderless", "");
        tabbedPanePage.getBorderDashboardTab().click();
        assertThat(tabbedPanePage.getBorderDashboardTab()).hasAttribute("active", "");

        tabbedPanePage.getHideBorderToggle().click();
        assertThat(tabbedPanePage.getBorderTabbedPane()).hasAttribute("hide-active-indicator", "");
        assertThat(tabbedPanePage.getBorderTabbedPane()).not().hasAttribute("borderless", "");
        tabbedPanePage.getBorderOrdersTab().click();
        assertThat(tabbedPanePage.getBorderOrdersTab()).hasAttribute("active", "");

        tabbedPanePage.getHideActiveIndicatorToggle().click();
        assertThat(tabbedPanePage.getBorderTabbedPane()).not().hasAttribute("hide-active-indicator", "");
        assertThat(tabbedPanePage.getBorderTabbedPane()).not().hasAttribute("borderless", "");
        tabbedPanePage.getBorderDashboardTab().click();
        assertThat(tabbedPanePage.getBorderDashboardTab()).hasAttribute("active", "");
    }
} 