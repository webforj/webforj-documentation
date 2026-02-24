package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.avatar.AvatarShapesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarShapesViewIT extends BaseTest {

  private AvatarShapesPage avatarPage;

  public void setupAvatarDemo(SupportedLanguage language) {
    navigateToRoute(AvatarShapesPage.getRoute(language));
    avatarPage = new AvatarShapesPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCircleAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getCircleAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSquareAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getSquareAvatar()).isVisible();
  }
}
