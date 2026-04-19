package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.badge.BadgeThemesPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BadgeThemesViewIT extends BaseTest {

    private BadgeThemesPage badgeThemesPage;

    public void setupBadgeThemes(SupportedLanguage language) {
        navigateToRoute(BadgeThemesPage.getRoute(language));
        badgeThemesPage = new BadgeThemesPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testFilledPrimaryBadgeHasCorrectTheme(SupportedLanguage language) {
        setupBadgeThemes(language);
        assertThat(badgeThemesPage.getPrimaryBadge()).hasAttribute("theme", "primary");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testFilledSuccessBadgeHasCorrectTheme(SupportedLanguage language) {
        setupBadgeThemes(language);
        assertThat(badgeThemesPage.getSuccessBadge()).hasAttribute("theme", "success");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testFilledDangerBadgeHasCorrectTheme(SupportedLanguage language) {
        setupBadgeThemes(language);
        assertThat(badgeThemesPage.getDangerBadge()).hasAttribute("theme", "danger");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testOutlinedPrimaryBadgeHasCorrectTheme(SupportedLanguage language) {
        setupBadgeThemes(language);
        assertThat(badgeThemesPage.getOutlinedPrimaryBadge()).hasAttribute("theme", "outlined-primary");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testOutlinedSuccessBadgeHasCorrectTheme(SupportedLanguage language) {
        setupBadgeThemes(language);
        assertThat(badgeThemesPage.getOutlinedSuccessBadge()).hasAttribute("theme", "outlined-success");
    }
}
