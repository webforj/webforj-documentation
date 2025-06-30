package tests.DrawerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DrawerPage.DrawerAutoFocusPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DrawerAutoFocusIT extends BaseTest {

    private DrawerAutoFocusPage drawerAutoFocusPage;

    @BeforeEach
    public void setupDrawerAutoFocus() {
        navigateToRoute(DrawerAutoFocusPage.getRoute());
        drawerAutoFocusPage = new DrawerAutoFocusPage(page);
    }

    @BrowserTest
    public void testCheckboxClickableAndCloseWithX() {
        drawerAutoFocusPage.getCheckbox().click();
        assertThat(drawerAutoFocusPage.getCheckbox()).isChecked();

        drawerAutoFocusPage.getCloseButton().click();
        assertThat(drawerAutoFocusPage.getDrawer()).isHidden();
    }
} 