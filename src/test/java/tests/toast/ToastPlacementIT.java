package tests.toast;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.toast.ToastPlacementPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ToastPlacementIT extends BaseTest {

    private ToastPlacementPage toastPlacementsPage;

    @BeforeEach
    public void setupToastPlacements() {
        navigateToRoute(ToastPlacementPage.getRoute());
        toastPlacementsPage = new ToastPlacementPage(page);
    }

    @BrowserTest
    public void testToastPlacements() {
        toastPlacementsPage.getPlacementDropdown().click();
        toastPlacementsPage.getTopListItem().waitFor();
        toastPlacementsPage.getTopListItem().click();

        toastPlacementsPage.getPlacementButton().click();

        assertThat(toastPlacementsPage.getTopToastGroup()).isVisible();
    }
}