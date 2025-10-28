package com.webforj.samples.views.applayout.applayout;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.applayout.applayout.AppLayoutPage;
import com.webforj.samples.views.BaseTest;

public class AppLayoutViewIT extends BaseTest{

    private AppLayoutPage appLayoutPage;

    @BeforeEach
    public void setupAppLayout() {
        navigateToRoute(AppLayoutPage.getRoute());
        appLayoutPage = new AppLayoutPage(page);
    }

    @Test
    public void testDashboardLink() {
        appLayoutPage.getDashboardLink().click();
        assertThat(page.getByText("Content for Dashboard")).isVisible();
    }
}
