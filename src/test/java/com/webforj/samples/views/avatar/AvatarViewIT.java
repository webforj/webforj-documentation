package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.avatar.AvatarPage;
import com.webforj.samples.views.BaseTest;

public class AvatarViewIT extends BaseTest {

    private AvatarPage avatarPage;

    @BeforeEach
    public void setupAvatarDemo() {
        navigateToRoute(AvatarPage.getRoute());
        avatarPage = new AvatarPage(page);
    }

    @Test
    public void testPanelIsVisible() {
        assertThat(avatarPage.getPanel()).isVisible();
    }

    @Test
    public void testProjectHeaderIsVisible() {
        assertThat(avatarPage.getProjectHeader()).isVisible();
    }

    @Test
    public void testTeamLabelIsVisible() {
        assertThat(avatarPage.getTeamLabel()).isVisible();
    }

    @Test
    public void testSarahAvatarIsVisible() {
        assertThat(avatarPage.getSarahAvatar()).isVisible();
    }

    @Test
    public void testMarcusAvatarIsVisible() {
        assertThat(avatarPage.getMarcusAvatar()).isVisible();
    }

    @Test
    public void testElenaAvatarIsVisible() {
        assertThat(avatarPage.getElenaAvatar()).isVisible();
    }

    @Test
    public void testDavidAvatarIsVisible() {
        assertThat(avatarPage.getDavidAvatar()).isVisible();
    }

    @Test
    public void testDialogOpensWhenAvatarIsClicked() {
        avatarPage.getSarahAvatar().click();
        assertThat(avatarPage.getDialog()).isVisible();
    }
}