package tests.TabbedPane;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TabbedPanePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TabbedPanePlacementIT extends BaseTest {

    private TabbedPanePage tabbedPanePage;

    @BeforeEach
    public void setupTabbedPanePlacement() {
        page.navigate("https://docs.webforj.com/webforj/tabbedpaneplacement?");
        tabbedPanePage = new TabbedPanePage(page);
    }

    @BrowserTest
    public void testPlacement() {
        tabbedPanePage.getPlacementDropdown().click();
        tabbedPanePage.getPlacementListBox().locator("text=TOP").click();
        assertThat(tabbedPanePage.getPlacementTabbedPane()).hasAttribute("placement", "top");

        tabbedPanePage.getPlacementDropdown().click();
        tabbedPanePage.getPlacementListBox().locator("text=BOTTOM").click();
        assertThat(tabbedPanePage.getPlacementTabbedPane()).hasAttribute("placement", "bottom");

        tabbedPanePage.getPlacementDropdown().click();
        tabbedPanePage.getPlacementListBox().locator("text=LEFT").click();
        assertThat(tabbedPanePage.getPlacementTabbedPane()).hasAttribute("placement", "left");

        tabbedPanePage.getPlacementDropdown().click();
        tabbedPanePage.getPlacementListBox().locator("text=RIGHT").click();
        assertThat(tabbedPanePage.getPlacementTabbedPane()).hasAttribute("placement", "right");

        tabbedPanePage.getPlacementDropdown().click();
        tabbedPanePage.getPlacementListBox().locator("text=HIDDEN").click();
        assertThat(tabbedPanePage.getPlacementTabbedPane()).hasAttribute("placement", "hidden");

    }
} 