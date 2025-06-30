package tests.DrawerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import com.microsoft.playwright.APIResponse;

import pages.DrawerPage.DrawerWelcomePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DrawerWelcomeIT extends BaseTest {

    private DrawerWelcomePage drawerWelcomePage;

    @BeforeEach
    public void setupDrawerWelcome() {
        navigateToRoute(DrawerWelcomePage.getRoute());
        drawerWelcomePage = new DrawerWelcomePage(page);
    }

    @Disabled("Fix it later")
    @BrowserTest
    public void testDrawerWelcomePageElements() {
        String handshakeImage = page.locator("div[dwc-id='28'] img").getAttribute("src");
        assertNotNull(handshakeImage, "Image src should not be null");

        String BBJImage = page.locator("div[dwc-id='16'] img").getAttribute("src");
        assertNotNull(BBJImage, "Image src should not be null");

        APIResponse response = page.context().request().get(handshakeImage);
        assertEquals(200, response.status());

        APIResponse response2 = page.context().request().get(BBJImage);
        assertEquals(200, response2.status());

        assertThat(drawerWelcomePage.getDrawerTitle()).hasText("Drawer");
    }

    @Disabled("Fix it later")
    @BrowserTest
    public void testCloseWithXAndReopenViaOpenWelcomePage() {
        drawerWelcomePage.getCloseButton().click();

        drawerWelcomePage.getOpenWelcomePageButton().click();
        assertThat(drawerWelcomePage.getWelcomeDrawer()).isVisible();
    }

    @Disabled("Bug Report #361")
    @BrowserTest
    public void testGetStartedNavigatesToMainAndReopenDrawer() {
        drawerWelcomePage.getStartButton().click();
        drawerWelcomePage.getOpenWelcomePageButton().click();
        assertThat(drawerWelcomePage.getWelcomeDrawer()).isVisible();
    }
} 