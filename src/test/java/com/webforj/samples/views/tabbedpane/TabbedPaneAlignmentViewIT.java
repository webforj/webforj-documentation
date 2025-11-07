package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.tabbedpane.TabbedPaneAlignmentPage;
import com.webforj.samples.views.BaseTest;

public class TabbedPaneAlignmentViewIT extends BaseTest {

    private TabbedPaneAlignmentPage tabbedPaneAlignmentPage;

    @BeforeEach
    public void setupAlignmentTabbedPane() {
        navigateToRoute(TabbedPaneAlignmentPage.getRoute());
        tabbedPaneAlignmentPage = new TabbedPaneAlignmentPage(page);
    }

    @Test
    public void testAlignmentTabbedPane() {
        tabbedPaneAlignmentPage.getAlignmentDropdown().click();
        tabbedPaneAlignmentPage.getAlignmentDropdownButton().click(); //CENTER
        assertThat(tabbedPaneAlignmentPage.getAlignmentTabbedPane()).hasAttribute("alignment", "center");
    }
}
