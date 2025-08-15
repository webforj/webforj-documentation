package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.tabbedpane.TabbedPaneBorderPage;
import com.webforj.samples.views.BaseTest;

public class TabbedPaneBorderIT extends BaseTest {

    private TabbedPaneBorderPage tabbedPaneBorderPage;

    @BeforeEach
    public void setupBorder() {
        navigateToRoute(TabbedPaneBorderPage.getRoute());
        tabbedPaneBorderPage = new TabbedPaneBorderPage(page);
    }

    @Test
    public void testBorder() {
        tabbedPaneBorderPage.getHideBorderToggle().click();
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("borderless", "");

        tabbedPaneBorderPage.getHideActiveIndicatorToggle().click();
        assertThat(tabbedPaneBorderPage.getBorderTabbedPane()).hasAttribute("hide-active-indicator", "");

        tabbedPaneBorderPage.getOrdersTab().click();
        assertThat(tabbedPaneBorderPage.getOrdersTab()).hasAttribute("active", "");
        assertThat(tabbedPaneBorderPage.getDashboardTab()).not().hasAttribute("active", "");
    }
}
