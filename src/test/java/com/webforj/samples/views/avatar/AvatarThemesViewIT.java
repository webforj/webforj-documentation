package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.avatar.AvatarThemesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarThemesViewIT extends BaseTest {

  private AvatarThemesPage avatarPage;

  public void setupAvatarDemo(SupportedLanguage language) {
    navigateToRoute(AvatarThemesPage.getRoute(language));
    avatarPage = new AvatarThemesPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testDefaultThemeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getDefaultAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testGrayThemeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getGrayAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testPrimaryThemeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getPrimaryAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSuccessThemeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getSuccessAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testWarningThemeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getWarningAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testDangerThemeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getDangerAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInfoThemeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getInfoAvatar()).isVisible();
  }
}
