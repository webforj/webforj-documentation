package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;

import com.webforj.samples.pages.avatar.AvatarExpansesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarExpansesViewIT extends BaseTest {

  private AvatarExpansesPage avatarPage;

  public void setupAvatarDemo(SupportedLanguage language) {
    navigateToRoute(AvatarExpansesPage.getRoute(language));
    avatarPage = new AvatarExpansesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testXxxsmallAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getXxxsmallAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testXxsmallAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getXxsmallAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testXsmallAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getXsmallAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSmallAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getSmallAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testMediumAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getMediumAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testLargeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getLargeAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testXlargeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getXlargeAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testXxlargeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getXxlargeAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testXxxlargeAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getXxxlargeAvatar()).isVisible();
  }
}
