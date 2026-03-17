package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.avatar.AvatarPage;
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
    var names = avatarPage.getNames();
    for (int i = 0; i < names.size(); i++) {
      var nameLabel = avatarPage.getNameLabel(i);
      nameLabel.assertThat().hasValue(names.get(i));
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testRoleLabelHaveCorrectValues(SupportedLanguage language) {
    avatarPage.setRoute(language);
    var roles = avatarPage.getRoles();
    for (int i = 0; i < roles.size(); i++) {
      var roleLabel = avatarPage.getRoleLabel(i);
      roleLabel.assertThat().hasValue(roles.get(i));
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAvatarHaveCorrectThemes(SupportedLanguage language) {
    avatarPage.setRoute(language);
    var themes = avatarPage.getThemes();
    for (int i = 0; i < themes.size(); i++) {
      var avatar = avatarPage.getAvatar(i);
      avatar.assertThat().hasTheme(themes.get(i));
    }
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAvatarOpensDialogThenClicked(SupportedLanguage language) {
    avatarPage.setRoute(language);
    var projectLabel = avatarPage.getProjectNameLabel();
    for (int i = 0; i < 4; i++) {
      var avatar = avatarPage.getAvatar(i);
      var dialog = avatarPage.getDialog();
      dialog.assertThat().not().isVisible();
      avatar.click();
      dialog.assertThat().isVisible();
      projectLabel.click();
      dialog.assertThat().not().isVisible();
      assertFalse(dialog.isVisible(), avatarPage.getNameLabel(i).textContent());
    }
  }

}
