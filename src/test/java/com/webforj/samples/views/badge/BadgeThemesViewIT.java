package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.badge.BadgeThemesPage;
import com.webforj.samples.views.BaseTest;

public class BadgeThemesViewIT extends BaseTest {

    private BadgeThemesPage badgeThemesPage;

    @BeforeEach
    public void setupBadgeThemes() {
        navigateToRoute(BadgeThemesPage.getRoute());
        badgeThemesPage = new BadgeThemesPage(page);
    }

    @Test
    public void testFilledPrimaryBadgeHasCorrectTheme() {
        assertThat(badgeThemesPage.getPrimaryBadge()).hasAttribute("theme", "primary");
    }

    @Test
    public void testFilledSuccessBadgeHasCorrectTheme() {
        assertThat(badgeThemesPage.getSuccessBadge()).hasAttribute("theme", "success");
    }

    @Test
    public void testFilledDangerBadgeHasCorrectTheme() {
        assertThat(badgeThemesPage.getDangerBadge()).hasAttribute("theme", "danger");
    }

    @Test
    public void testOutlinedPrimaryBadgeHasCorrectTheme() {
        assertThat(badgeThemesPage.getOutlinedPrimaryBadge()).hasAttribute("theme", "outlined-primary");
    }

    @Test
    public void testOutlinedSuccessBadgeHasCorrectTheme() {
        assertThat(badgeThemesPage.getOutlinedSuccessBadge()).hasAttribute("theme", "outlined-success");
    }
}
