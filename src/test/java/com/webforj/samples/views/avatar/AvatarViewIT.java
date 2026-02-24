package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.avatar.AvatarPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarViewIT extends BaseTest {

  private AvatarPage avatarPage;

  public void setupAvatarDemo(SupportedLanguage language) {
    navigateToRoute(AvatarPage.getRoute(language));
    avatarPage = new AvatarPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testPanelIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getPanel()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testProjectHeaderIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getProjectHeader()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testTeamLabelIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getTeamLabel()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSarahAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getSarahAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testMarcusAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getMarcusAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testElenaAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getElenaAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testDavidAvatarIsVisible(SupportedLanguage language) {
    setupAvatarDemo(language);
    assertThat(avatarPage.getDavidAvatar()).isVisible();
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testDialogOpensWhenAvatarIsClicked(SupportedLanguage language) {
    setupAvatarDemo(language);
    avatarPage.getSarahAvatar().click();
    assertThat(avatarPage.getDialog()).isVisible();
  }
}
