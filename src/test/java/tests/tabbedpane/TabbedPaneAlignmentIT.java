package tests.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.tabbedpane.TabbedPaneAlignmentPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TabbedPaneAlignmentIT extends BaseTest {

    private TabbedPaneAlignmentPage tabbedPaneAlignmentPage;

    @BeforeEach
    public void setupAlignment() {
        navigateToRoute(TabbedPaneAlignmentPage.getRoute());
        tabbedPaneAlignmentPage = new TabbedPaneAlignmentPage(page);
    }

    @BrowserTest
    public void testAlignment() {
        tabbedPaneAlignmentPage.getAlignmentDropdown().click();
        tabbedPaneAlignmentPage.getAlignmentListBox().locator("text=AUTO").click();
        assertThat(tabbedPaneAlignmentPage.getAlignmentTabbedPane()).hasAttribute("alignment", "auto");

        tabbedPaneAlignmentPage.getAlignmentDropdown().click();
        tabbedPaneAlignmentPage.getAlignmentListBox().locator("text=CENTER").click();
        assertThat(tabbedPaneAlignmentPage.getAlignmentTabbedPane()).hasAttribute("alignment", "center");

        tabbedPaneAlignmentPage.getAlignmentDropdown().click();
        tabbedPaneAlignmentPage.getAlignmentListBox().locator("text=END").click();
        assertThat(tabbedPaneAlignmentPage.getAlignmentTabbedPane()).hasAttribute("alignment", "end");

        tabbedPaneAlignmentPage.getAlignmentDropdown().click();
        tabbedPaneAlignmentPage.getAlignmentListBox().locator("text=START").click();
        assertThat(tabbedPaneAlignmentPage.getAlignmentTabbedPane()).hasAttribute("alignment", "start");

        tabbedPaneAlignmentPage.getAlignmentDropdown().click();
        tabbedPaneAlignmentPage.getAlignmentListBox().locator("text=STRETCH").click();
        assertThat(tabbedPaneAlignmentPage.getAlignmentTabbedPane()).hasAttribute("alignment", "stretch");
    }
}