package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.avatar.AvatarPage;
import com.webforj.samples.utils.components.AvatarComponent;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarViewIT extends BaseTest {
  private AvatarPage avatarPage;

  @BeforeEach
  public void setupAvatarDemo() {
    avatarPage = new AvatarPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testProjectNameLabelIsVisible(SupportedLanguage language) {
    avatarPage.setRoute(language);
    avatarPage.getProjectNameLabel().assertThat().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAvatarPanelIsVisible(SupportedLanguage language) {
    avatarPage.setRoute(language);
    avatarPage.getAvatarPanel().assertThat().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testTeamLabelIsVisible(SupportedLanguage language) {
    avatarPage.setRoute(language);
    avatarPage.getTeamLabel().assertThat().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testNameLabelHaveCorrectValues(SupportedLanguage language) {
    avatarPage.setRoute(language);
    for (int i = 0; i < avatarPage.getNames().size(); i++) {
      avatarPage.getNameLabel(i).assertThat().hasText(avatarPage.getNames().get(i));
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testRoleLabelHaveCorrectValues(SupportedLanguage language) {
    avatarPage.setRoute(language);
    for (int i = 0; i < avatarPage.getRoles().size(); i++) {
      avatarPage.getRoleLabel(i).assertThat().hasText(avatarPage.getRoles().get(i));
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAvatarHaveCorrectThemes(SupportedLanguage language) {
    avatarPage.setRoute(language);
    for (int i = 0; i < avatarPage.getThemes().size(); i++) {
      AvatarComponent avatar = avatarPage.getAvatar(i);
      avatar.assertTheme(avatarPage.getThemes().get(i));
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testClickingAvatarOpensDialog(SupportedLanguage language) {
    avatarPage.setRoute(language);
    // Dialog should not be visible initially
    assertThat(avatarPage.getDialog()).not().isVisible();

    // Click on first avatar - dialog should open
    avatarPage.getAvatar(0).click();
    assertThat(avatarPage.getDialog()).isVisible();
  }

}
