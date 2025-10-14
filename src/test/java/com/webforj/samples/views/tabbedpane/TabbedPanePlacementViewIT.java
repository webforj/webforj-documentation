package com.webforj.samples.views.tabbedpane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.tabbedpane.TabbedPanePlacementPage;
import com.webforj.samples.views.BaseTest;

public class TabbedPanePlacementViewIT extends BaseTest {

    private TabbedPanePlacementPage tabbedPanePlacementPage;

    @BeforeEach
    public void setupPlacementTabbedPane() {
        navigateToRoute(TabbedPanePlacementPage.getRoute());
        tabbedPanePlacementPage = new TabbedPanePlacementPage(page);
    }

    @Test
    public void testPlacementTabbedPane() {
        tabbedPanePlacementPage.getPlacementDropdown().click();
        tabbedPanePlacementPage.getAlignmentDropdownButton().click(); //BOTTOM
        assertThat(tabbedPanePlacementPage.getPlacementTabbedPane()).hasAttribute("placement", "bottom");

    }
}
