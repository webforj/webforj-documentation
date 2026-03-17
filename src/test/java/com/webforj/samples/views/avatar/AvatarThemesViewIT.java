package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.avatar.AvatarTheme;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.avatar.AvatarThemesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarThemesViewIT extends BaseTest {
  private AvatarThemesPage avatarPage;

  @BeforeEach
  public void setupAvatarThemesDemo() {
    avatarPage = new AvatarThemesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAllAvatarThemesAreVisible(SupportedLanguage language) {
    avatarPage.setRoute(language);
    for (AvatarTheme theme : AvatarTheme.values()) {
      avatarPage.getAvatar(theme).assertThat().isVisible();
    }
  }

}
