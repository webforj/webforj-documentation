package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TabbedPanePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class AlignmentIT extends BaseTest {

    private TabbedPanePage tabbedPanePage;

    @BeforeEach
    public void setupAlignment() {
        page.navigate("https://docs.webforj.com/webforj/tabbedpanealignment?");
        tabbedPanePage = new TabbedPanePage(page);
    }

    @BrowserTest
    public void testAlignment() {
        tabbedPanePage.getAlignmentDropdown().click();
        tabbedPanePage.getAlignmentListBox().locator("text=AUTO").click();
        assertThat(tabbedPanePage.getAlignmentTabbedPane()).hasAttribute("alignment", "auto");

        tabbedPanePage.getAlignmentDropdown().click();
        tabbedPanePage.getAlignmentListBox().locator("text=CENTER").click();
        assertThat(tabbedPanePage.getAlignmentTabbedPane()).hasAttribute("alignment", "center");

        tabbedPanePage.getAlignmentDropdown().click();
        tabbedPanePage.getAlignmentListBox().locator("text=END").click();
        assertThat(tabbedPanePage.getAlignmentTabbedPane()).hasAttribute("alignment", "end");

        tabbedPanePage.getAlignmentDropdown().click();
        tabbedPanePage.getAlignmentListBox().locator("text=START").click();
        assertThat(tabbedPanePage.getAlignmentTabbedPane()).hasAttribute("alignment", "start");

        tabbedPanePage.getAlignmentDropdown().click();
        tabbedPanePage.getAlignmentListBox().locator("text=STRETCH").click();
        assertThat(tabbedPanePage.getAlignmentTabbedPane()).hasAttribute("alignment", "stretch");
    }
} 