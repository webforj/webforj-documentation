package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.avatar.AvatarThemesPage;
import com.webforj.samples.views.BaseTest;

public class AvatarThemesViewIT extends BaseTest {

    private AvatarThemesPage avatarPage;

    @BeforeEach
    public void setupAvatarDemo() {
        navigateToRoute(AvatarThemesPage.getRoute());
        avatarPage = new AvatarThemesPage(page);
    }

    @Test
    public void testDefaultThemeAvatarIsVisible() {
        assertThat(avatarPage.getDefaultAvatar()).isVisible();
    }

    @Test
    public void testGrayThemeAvatarIsVisible() {
        assertThat(avatarPage.getGrayAvatar()).isVisible();
    }

    @Test
    public void testPrimaryThemeAvatarIsVisible() {
        assertThat(avatarPage.getPrimaryAvatar()).isVisible();
    }

    @Test
    public void testSuccessThemeAvatarIsVisible() {
        assertThat(avatarPage.getSuccessAvatar()).isVisible();
    }

    @Test
    public void testWarningThemeAvatarIsVisible() {
        assertThat(avatarPage.getWarningAvatar()).isVisible();
    }

    @Test
    public void testDangerThemeAvatarIsVisible() {
        assertThat(avatarPage.getDangerAvatar()).isVisible();
    }

    @Test
    public void testInfoThemeAvatarIsVisible() {
        assertThat(avatarPage.getInfoAvatar()).isVisible();
    }
}