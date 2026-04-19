package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.badge.BadgeSizesPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BadgeSizesViewIT extends BaseTest {

    private BadgeSizesPage badgeSizesPage;

    public void setupBadgeSizes(SupportedLanguage language) {
        navigateToRoute(BadgeSizesPage.getRoute(language));
        badgeSizesPage = new BadgeSizesPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testAllNineSizeBadgesAreRendered(SupportedLanguage language) {
        setupBadgeSizes(language);
        // 9 size badges + 5 circular badges = 14 total
        assertThat(badgeSizesPage.getAllSizeBadges()).hasCount(14);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSizeBadgesHaveCorrectExpanses(SupportedLanguage language) {
        setupBadgeSizes(language);
        // Size badges are rendered in order: 3xs, 2xs, xs, s, m, l, xl, 2xl, 3xl
        assertThat(badgeSizesPage.getSizeBadgeByIndex(0)).hasAttribute("expanse", "3xs");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(2)).hasAttribute("expanse", "xs");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(4)).hasAttribute("expanse", "m");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(6)).hasAttribute("expanse", "xl");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(8)).hasAttribute("expanse", "3xl");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testCircularBadgesAreVisible(SupportedLanguage language) {
        setupBadgeSizes(language);
        assertThat(badgeSizesPage.getAllCircularBadges()).hasCount(5);
        assertThat(badgeSizesPage.getAllCircularBadges().first()).isVisible();
    }
}
