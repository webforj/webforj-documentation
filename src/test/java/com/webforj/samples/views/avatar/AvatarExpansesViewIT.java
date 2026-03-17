package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.avatar.AvatarExpansesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarExpansesViewIT extends BaseTest {

  private AvatarExpansesPage avatarPage;

  @BeforeEach
  public void setupAvatarExpansesDemo() {
    avatarPage = new AvatarExpansesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAvatarHasCorrectExpanse(SupportedLanguage language) {
    avatarPage.setRoute(language);
    for (AvatarExpanse expanse : AvatarExpanse.values()) {
      if (expanse == AvatarExpanse.NONE) { // Not used in the Expanse View
        continue;
      }
      var avatar = avatarPage.getAvatar(expanse);
      avatar.assertThat().isVisible();
    }
  }

}
