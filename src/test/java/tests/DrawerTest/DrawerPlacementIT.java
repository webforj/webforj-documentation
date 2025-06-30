package tests.DrawerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DrawerPage.DrawerPlacementPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DrawerPlacementIT extends BaseTest {

    private DrawerPlacementPage drawerPlacementPage;

    @BeforeEach
    public void setupDrawerPlacement() {
        navigateToRoute(DrawerPlacementPage.getRoute());
        drawerPlacementPage = new DrawerPlacementPage(page);
    }

    @BrowserTest
    public void testDrawerAppearsInEachPlacementAndCloseWithX() {
        drawerPlacementPage.getDrawerDropdown().click();
        drawerPlacementPage.getListBox("Left").click();

        assertThat(drawerPlacementPage.getDrawer()).hasAttribute("placement", "left");

        drawerPlacementPage.getDrawerDropdown().click();
        drawerPlacementPage.getListBox("Top").click();

        assertThat(drawerPlacementPage.getDrawer()).hasAttribute("placement", "top");

        drawerPlacementPage.getDrawerDropdown().click();
        drawerPlacementPage.getListBox("Top_center").click();

        assertThat(drawerPlacementPage.getDrawer()).hasAttribute("placement", "top-center");

        drawerPlacementPage.getDrawerDropdown().click();
        drawerPlacementPage.getListBox("Bottom").click();

        assertThat(drawerPlacementPage.getDrawer()).hasAttribute("placement", "bottom");

        drawerPlacementPage.getDrawerDropdown().click();
        drawerPlacementPage.getListBox("Bottom_center").click();

        assertThat(drawerPlacementPage.getDrawer()).hasAttribute("placement", "bottom-center");

        drawerPlacementPage.getDrawerDropdown().click();
        drawerPlacementPage.getListBox("Right").click();

        assertThat(drawerPlacementPage.getDrawer()).hasAttribute("placement", "right");
    }
} 