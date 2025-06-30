package tests.DrawerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DrawerPage.DrawerDemoPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DrawerDemoIT extends BaseTest {

    private DrawerDemoPage drawerDemoPage;

    @BeforeEach
    public void setupDrawerDemo() {
        navigateToRoute(DrawerDemoPage.getRoute());
        drawerDemoPage = new DrawerDemoPage(page);
    }

    @BrowserTest
    public void testDrawerVisibilityAndCloseWithX() {
        assertThat(drawerDemoPage.getDrawer()).isVisible();
        drawerDemoPage.getCloseButton().click();
        assertThat(drawerDemoPage.getDrawer()).isHidden();
    }
} 