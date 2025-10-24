package com.webforj.samples.views.applayout.fullnavbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.applayout.fullnavbar.AppLayoutFullNavbarPage;
import com.webforj.samples.views.BaseTest;

public class AppLayoutFullNavbarViewIT extends BaseTest {

    private AppLayoutFullNavbarPage appLayoutFullNavbarPage;

    @BeforeEach
    public void setupAppLayoutFullNavbar() {
        navigateToRoute(AppLayoutFullNavbarPage.getRoute());
        appLayoutFullNavbarPage = new AppLayoutFullNavbarPage(page);
    }

    @Test
    public void testHeaderOffScreen() {
        assertThat(appLayoutFullNavbarPage.getHeaderText()).isEmpty();

    }

}
