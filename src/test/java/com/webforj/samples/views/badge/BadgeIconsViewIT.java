package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.badge.BadgeIconsPage;
import com.webforj.samples.views.BaseTest;

public class BadgeIconsViewIT extends BaseTest {

    private BadgeIconsPage badgeIconsPage;

    @BeforeEach
    public void setupBadgeIcons() {
        navigateToRoute(BadgeIconsPage.getRoute());
        badgeIconsPage = new BadgeIconsPage(page);
    }

    @Test
    public void testDoneBadgeIsVisible() {
        assertThat(badgeIconsPage.getDoneBadge()).isVisible();
    }

    @Test
    public void testDoneBadgeHasSuccessTheme() {
        assertThat(badgeIconsPage.getDoneBadge()).hasAttribute("theme", "success");
    }

    @Test
    public void testErrorBadgeHasDangerTheme() {
        assertThat(badgeIconsPage.getErrorBadge()).hasAttribute("theme", "danger");
    }

    @Test
    public void testIconOnlyAndIconTextBadgesAreRendered() {
        // The view renders 3 groups: icon+text (4), icon-only (4), all themes (5) = 13
        assertThat(badgeIconsPage.getAllBadges()).hasCount(13);
    }
}
