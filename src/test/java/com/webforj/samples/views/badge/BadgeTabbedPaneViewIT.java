package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.badge.BadgeTabbedPanePage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BadgeTabbedPaneViewIT extends BaseTest {

    private BadgeTabbedPanePage badgeTabbedPanePage;

    public void setupBadgeTabbedPane(SupportedLanguage language) {
        navigateToRoute(BadgeTabbedPanePage.getRoute(language));
        badgeTabbedPanePage = new BadgeTabbedPanePage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInboxTabIsVisible(SupportedLanguage language) {
        setupBadgeTabbedPane(language);
        assertThat(badgeTabbedPanePage.getInboxTab()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInboxTabBadgeShowsCount(SupportedLanguage language) {
        setupBadgeTabbedPane(language);
        assertThat(badgeTabbedPanePage.getInboxTab()
                .locator("dwc-badge")).hasText("12");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testNotificationsTabBadgeShowsCount(SupportedLanguage language) {
        setupBadgeTabbedPane(language);
        assertThat(badgeTabbedPanePage.getNotificationsTab()
                .locator("dwc-badge")).hasText("3");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDraftsTabBadgeShowsCount(SupportedLanguage language) {
        setupBadgeTabbedPane(language);
        assertThat(badgeTabbedPanePage.getDraftsTab()
                .locator("dwc-badge")).hasText("1");
    }
}
