package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.avatar.AvatarShapesPage;
import com.webforj.samples.views.BaseTest;

public class AvatarShapesViewIT extends BaseTest {

    private AvatarShapesPage avatarPage;

    @BeforeEach
    public void setupAvatarDemo() {
        navigateToRoute(AvatarShapesPage.getRoute());
        avatarPage = new AvatarShapesPage(page);
    }

    @Test
    public void testCircleAvatarIsVisible() {
        assertThat(avatarPage.getCircleAvatar()).isVisible();
    }

    @Test
    public void testSquareAvatarIsVisible() {
        assertThat(avatarPage.getSquareAvatar()).isVisible();
    }
}