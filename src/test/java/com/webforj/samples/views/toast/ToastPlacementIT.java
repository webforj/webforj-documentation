package com.webforj.samples.views.toast;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.toast.ToastPlacementPage;
import com.webforj.samples.views.BaseTest;

public class ToastPlacementIT extends BaseTest {

    private ToastPlacementPage toastPlacementsPage;

    @BeforeEach
    public void setupToastPlacements() {
        navigateToRoute(ToastPlacementPage.getRoute());
        toastPlacementsPage = new ToastPlacementPage(page);
    }

    @Test
    public void testToastPlacements() {
        toastPlacementsPage.getPlacementDropdown().click();
        toastPlacementsPage.getTopListItem().waitFor();
        toastPlacementsPage.getTopListItem().click();

        toastPlacementsPage.getPlacementButton().click();

        assertThat(toastPlacementsPage.getTopToastGroup()).isVisible();
    }
}
