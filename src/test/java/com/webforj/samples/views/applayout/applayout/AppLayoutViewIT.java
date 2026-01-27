package com.webforj.samples.views.applayout.applayout;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.applayout.applayout.AppLayoutPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AppLayoutViewIT extends BaseTest{

    private AppLayoutPage appLayoutPage;

    @Override
    protected String getRoute(SupportedLanguage language) {
        return AppLayoutPage.getRoute(language);
    }

    public void setupAppLayout(String route) {
        navigateToRoute(route);
        appLayoutPage = new AppLayoutPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDashboardLink(String route) {
        setupAppLayout(route);
        appLayoutPage.getDashboardLink().click();
        assertThat(page.getByText("Content for Dashboard")).isVisible();
    }
}
