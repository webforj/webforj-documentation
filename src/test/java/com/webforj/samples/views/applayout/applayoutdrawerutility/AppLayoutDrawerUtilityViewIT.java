package com.webforj.samples.views.applayout.applayoutdrawerutility;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.applayout.applayoutdrawerutility.AppLayoutDrawerUtilityPage;
import com.webforj.samples.views.BaseTest;

public class AppLayoutDrawerUtilityViewIT extends BaseTest{

    private AppLayoutDrawerUtilityPage appLayoutDrawerUtilityPage;

    @BeforeEach
    public void setupAppLayoutDrawerUtility() {
        navigateToRoute(AppLayoutDrawerUtilityPage.getRoute());
        appLayoutDrawerUtilityPage = new AppLayoutDrawerUtilityPage(page);
    }

    @Test
    public void testHeaderText() {
        assertThat(appLayoutDrawerUtilityPage.getHeaderText()).isVisible();
    }

}
