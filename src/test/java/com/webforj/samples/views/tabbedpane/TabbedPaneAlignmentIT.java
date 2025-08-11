package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.tabbedpane.TabbedPaneAlignmentPage;
import com.webforj.samples.views.BaseTest;

public class TabbedPaneAlignmentIT extends BaseTest {

    private TabbedPaneAlignmentPage tabbedPaneAlignmentPage;

    @BeforeEach
    public void setupAlignment() {
        navigateToRoute(TabbedPaneAlignmentPage.getRoute());
        tabbedPaneAlignmentPage = new TabbedPaneAlignmentPage(page);
    }

    @Test
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
