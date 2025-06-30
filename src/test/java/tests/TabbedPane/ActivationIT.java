package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import pages.TabbedPanePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ActivationIT extends BaseTest {

    private TabbedPanePage tabbedPanePage;

    @BeforeEach
    public void setupActivation() {
        page.navigate("https://docs.webforj.com/webforj/tabbedpaneactivation?");
        tabbedPanePage = new TabbedPanePage(page);
    }

    @Disabled("Bug Report #983")
    @BrowserTest
    public void testActivation() {
        tabbedPanePage.getActivationDashboardTab().click();
        page.keyboard().press("ArrowRight");
        assertThat(tabbedPanePage.getActivationOrdersTab()).hasAttribute("data-js-focus-visible", "");
        assertThat(tabbedPanePage.getActivationOrdersTab()).not().hasAttribute("active", "");

        tabbedPanePage.getActivationToggle().click();
        tabbedPanePage.getActivationDashboardTab().click();
        page.keyboard().press("ArrowRight");
        assertThat(tabbedPanePage.getActivationOrdersTab()).hasAttribute("data-js-focus-visible", "");
        assertThat(tabbedPanePage.getActivationOrdersTab()).hasAttribute("active", "");

        tabbedPanePage.getActivationToggle().click();
        tabbedPanePage.getActivationDashboardTab().click();
        page.keyboard().press("ArrowRight");
        assertThat(tabbedPanePage.getActivationOrdersTab()).hasAttribute("data-js-focus-visible", "");
        assertThat(tabbedPanePage.getActivationOrdersTab()).not().hasAttribute("active", "");

    }
} 