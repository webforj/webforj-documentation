package tests.ToastTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ToastPage.ToastPlacementsPage;

import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ToastPlacementsIT extends BaseTest {

    private ToastPlacementsPage toastPlacementsPage;

    @BeforeEach
    public void setupToastPlacements() {
        navigateToRoute(ToastPlacementsPage.getRoute());
        toastPlacementsPage = new ToastPlacementsPage(page);
    }

    @BrowserTest
    public void testToastPlacements() {
        toastPlacementsPage.getPlacementDropdown().click();
        toastPlacementsPage.getTopListItem().waitFor();
        toastPlacementsPage.getTopListItem().click();

        toastPlacementsPage.getPlacementButton().click();

        WaitUtil.waitForVisible(toastPlacementsPage.getTopToastGroup());
        assertThat(toastPlacementsPage.getTopToastGroup()).isVisible();
    }
}