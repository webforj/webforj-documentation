package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.avatar.AvatarExpansesPage;
import com.webforj.samples.views.BaseTest;

public class AvatarExpansesViewIT extends BaseTest {

    private AvatarExpansesPage avatarPage;

    @BeforeEach
    public void setupAvatarDemo() {
        navigateToRoute(AvatarExpansesPage.getRoute());
        avatarPage = new AvatarExpansesPage(page);
    }

    @Test
    public void testXxxsmallAvatarIsVisible() {
        assertThat(avatarPage.getXxxsmallAvatar()).isVisible();
    }

    @Test
    public void testXxsmallAvatarIsVisible() {
        assertThat(avatarPage.getXxsmallAvatar()).isVisible();
    }

    @Test
    public void testXsmallAvatarIsVisible() {
        assertThat(avatarPage.getXsmallAvatar()).isVisible();
    }

    @Test
    public void testSmallAvatarIsVisible() {
        assertThat(avatarPage.getSmallAvatar()).isVisible();
    }

    @Test
    public void testMediumAvatarIsVisible() {
        assertThat(avatarPage.getMediumAvatar()).isVisible();
    }

    @Test
    public void testLargeAvatarIsVisible() {
        assertThat(avatarPage.getLargeAvatar()).isVisible();
    }

    @Test
    public void testXlargeAvatarIsVisible() {
        assertThat(avatarPage.getXlargeAvatar()).isVisible();
    }

    @Test
    public void testXxlargeAvatarIsVisible() {
        assertThat(avatarPage.getXxlargeAvatar()).isVisible();
    }

    @Test
    public void testXxxlargeAvatarIsVisible() {
        assertThat(avatarPage.getXxxlargeAvatar()).isVisible();
    }
}