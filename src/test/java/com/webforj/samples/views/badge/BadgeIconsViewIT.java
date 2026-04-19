package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.badge.BadgeIconsPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BadgeIconsViewIT extends BaseTest {

    private BadgeIconsPage badgeIconsPage;

    public void setupBadgeIcons(SupportedLanguage language) {
        navigateToRoute(BadgeIconsPage.getRoute(language));
        badgeIconsPage = new BadgeIconsPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDoneBadgeIsVisible(SupportedLanguage language) {
        setupBadgeIcons(language);
        assertThat(badgeIconsPage.getDoneBadge()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDoneBadgeHasSuccessTheme(SupportedLanguage language) {
        setupBadgeIcons(language);
        assertThat(badgeIconsPage.getDoneBadge()).hasAttribute("theme", "success");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testErrorBadgeHasDangerTheme(SupportedLanguage language) {
        setupBadgeIcons(language);
        assertThat(badgeIconsPage.getErrorBadge()).hasAttribute("theme", "danger");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testIconOnlyAndIconTextBadgesAreRendered(SupportedLanguage language) {
        setupBadgeIcons(language);
        // The view renders 3 groups: icon+text (4), icon-only (4), all themes (5) = 13
        assertThat(badgeIconsPage.getAllBadges()).hasCount(13);
    }
}
