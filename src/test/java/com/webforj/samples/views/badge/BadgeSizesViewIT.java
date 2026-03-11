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
    public void testXsmallBadgeHasCorrectExpanse() {
        assertThat(badgeSizesPage.getXsmallBadge()).hasAttribute("expanse", "xs");
    }

    @Test
    public void testMediumBadgeHasCorrectExpanse() {
        assertThat(badgeSizesPage.getMediumBadge()).hasAttribute("expanse", "m");
    }

    @Test
    public void testXlargeBadgeHasCorrectExpanse() {
        assertThat(badgeSizesPage.getXlargeBadge()).hasAttribute("expanse", "xl");
    }

    @Test
    public void testCircularBadgeIsVisible() {
        assertThat(badgeSizesPage.getCircularBadge()).isVisible();
    }
}
