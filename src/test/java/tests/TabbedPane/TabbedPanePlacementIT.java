package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TabbedPanePage.TabbedPanePlacementPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TabbedPanePlacementIT extends BaseTest {

    private TabbedPanePlacementPage tabbedPanePlacementPage;

    @BeforeEach
    public void setupTabbedPanePlacement() {
        navigateToRoute(TabbedPanePlacementPage.getRoute());
        tabbedPanePlacementPage = new TabbedPanePlacementPage(page);
    }

    @BrowserTest
    public void testPlacement() {
        tabbedPanePlacementPage.getPlacementDropdown().click();
        tabbedPanePlacementPage.getPlacementListBox().locator("text=TOP").click();
        assertThat(tabbedPanePlacementPage.getPlacementTabbedPane()).hasAttribute("placement", "top");

        tabbedPanePlacementPage.getPlacementDropdown().click();
        tabbedPanePlacementPage.getPlacementListBox().locator("text=BOTTOM").click();
        assertThat(tabbedPanePlacementPage.getPlacementTabbedPane()).hasAttribute("placement", "bottom");

        tabbedPanePlacementPage.getPlacementDropdown().click();
        tabbedPanePlacementPage.getPlacementListBox().locator("text=LEFT").click();
        assertThat(tabbedPanePlacementPage.getPlacementTabbedPane()).hasAttribute("placement", "left");

        tabbedPanePlacementPage.getPlacementDropdown().click();
        tabbedPanePlacementPage.getPlacementListBox().locator("text=RIGHT").click();
        assertThat(tabbedPanePlacementPage.getPlacementTabbedPane()).hasAttribute("placement", "right");

        tabbedPanePlacementPage.getPlacementDropdown().click();
        tabbedPanePlacementPage.getPlacementListBox().locator("text=HIDDEN").click();
        assertThat(tabbedPanePlacementPage.getPlacementTabbedPane()).hasAttribute("placement", "hidden");

    }
}