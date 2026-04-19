package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.badge.BadgeButtonsPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BadgeButtonsViewIT extends BaseTest {

    private BadgeButtonsPage badgeButtonsPage;

    public void setupBadgeButtons(SupportedLanguage language) {
        navigateToRoute(BadgeButtonsPage.getRoute(language));
        badgeButtonsPage = new BadgeButtonsPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testNotificationsButtonIsVisible(SupportedLanguage language) {
        setupBadgeButtons(language);
        assertThat(badgeButtonsPage.getNotificationsButton()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testNotificationsButtonHasBadge(SupportedLanguage language) {
        setupBadgeButtons(language);
        assertThat(badgeButtonsPage.getNotificationsButton()
                .locator("dwc-badge")).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testNotificationsButtonBadgeShowsCount(SupportedLanguage language) {
        setupBadgeButtons(language);
        assertThat(badgeButtonsPage.getNotificationsButton()
                .locator("dwc-badge")).hasText("5");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testMessagesButtonBadgeShowsCount(SupportedLanguage language) {
        setupBadgeButtons(language);
        assertThat(badgeButtonsPage.getMessagesButton()
                .locator("dwc-badge")).hasText("12");
    }
}
