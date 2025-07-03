package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import pages.TabbedPanePage.TabbedPaneActivationPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TabbedPaneActivationIT extends BaseTest {

    private TabbedPaneActivationPage tabbedPaneActivationPage;

    @BeforeEach
    public void setupActivation() {
        page.navigate("https://docs.webforj.com/webforj/tabbedpaneactivation?");
        tabbedPaneActivationPage = new TabbedPaneActivationPage(page);
    }

    @Disabled("Bug Report #983")
    @BrowserTest
    public void testActivation() {
        tabbedPaneActivationPage.getActivationDashboardTab().click();
        page.keyboard().press("ArrowRight");
        assertThat(tabbedPaneActivationPage.getActivationOrdersTab()).hasAttribute("data-js-focus-visible", "");
        assertThat(tabbedPaneActivationPage.getActivationOrdersTab()).not().hasAttribute("active", "");

        tabbedPaneActivationPage.getActivationToggle().click();
        tabbedPaneActivationPage.getActivationDashboardTab().click();
        page.keyboard().press("ArrowRight");
        assertThat(tabbedPaneActivationPage.getActivationOrdersTab()).hasAttribute("data-js-focus-visible", "");
        assertThat(tabbedPaneActivationPage.getActivationOrdersTab()).hasAttribute("active", "");

        tabbedPaneActivationPage.getActivationToggle().click();
        tabbedPaneActivationPage.getActivationDashboardTab().click();
        page.keyboard().press("ArrowRight");
        assertThat(tabbedPaneActivationPage.getActivationOrdersTab()).hasAttribute("data-js-focus-visible", "");
        assertThat(tabbedPaneActivationPage.getActivationOrdersTab()).not().hasAttribute("active", "");

    }
}