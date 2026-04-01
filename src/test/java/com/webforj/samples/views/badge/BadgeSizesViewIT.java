package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.badge.BadgeSizesPage;
import com.webforj.samples.views.BaseTest;

public class BadgeSizesViewIT extends BaseTest {

    private BadgeSizesPage badgeSizesPage;

    @BeforeEach
    public void setupBadgeSizes() {
        navigateToRoute(BadgeSizesPage.getRoute());
        badgeSizesPage = new BadgeSizesPage(page);
    }

    @Test
    public void testAllNineSizeBadgesAreRendered() {
        // 9 size badges + 5 circular badges = 14 total
        assertThat(badgeSizesPage.getAllSizeBadges()).hasCount(14);
    }

    @Test
    public void testSizeBadgesHaveCorrectExpanses() {
        // Size badges are rendered in order: 3xs, 2xs, xs, s, m, l, xl, 2xl, 3xl
        assertThat(badgeSizesPage.getSizeBadgeByIndex(0)).hasAttribute("expanse", "3xs");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(2)).hasAttribute("expanse", "xs");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(4)).hasAttribute("expanse", "m");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(6)).hasAttribute("expanse", "xl");
        assertThat(badgeSizesPage.getSizeBadgeByIndex(8)).hasAttribute("expanse", "3xl");
    }

    @Test
    public void testCircularBadgesAreVisible() {
        assertThat(badgeSizesPage.getAllCircularBadges()).hasCount(5);
        assertThat(badgeSizesPage.getAllCircularBadges().first()).isVisible();
    }
}
