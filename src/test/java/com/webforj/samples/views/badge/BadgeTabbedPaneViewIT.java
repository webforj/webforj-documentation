package com.webforj.samples.views.badge;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.badge.BadgeTabbedPanePage;
import com.webforj.samples.views.BaseTest;

public class BadgeTabbedPaneViewIT extends BaseTest {

    private BadgeTabbedPanePage badgeTabbedPanePage;

    @BeforeEach
    public void setupBadgeTabbedPane() {
        navigateToRoute(BadgeTabbedPanePage.getRoute());
        badgeTabbedPanePage = new BadgeTabbedPanePage(page);
    }

    @Test
    public void testInboxTabIsVisible() {
        assertThat(badgeTabbedPanePage.getInboxTab()).isVisible();
    }

    @Test
    public void testInboxTabBadgeShowsCount() {
        assertThat(badgeTabbedPanePage.getInboxTab()
                .locator("dwc-badge")).hasText("12");
    }

    @Test
    public void testNotificationsTabBadgeShowsCount() {
        assertThat(badgeTabbedPanePage.getNotificationsTab()
                .locator("dwc-badge")).hasText("3");
    }

    @Test
    public void testDraftsTabBadgeShowsCount() {
        assertThat(badgeTabbedPanePage.getDraftsTab()
                .locator("dwc-badge")).hasText("1");
    }
}
