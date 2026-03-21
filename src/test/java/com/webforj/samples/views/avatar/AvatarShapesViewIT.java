package com.webforj.samples.views.avatar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.avatar.AvatarShape;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.avatar.AvatarShapesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AvatarShapesViewIT extends BaseTest {
  private AvatarShapesPage avatarPage;

  @BeforeEach
  public void setupAvatarShapesDemo() {
    avatarPage = new AvatarShapesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAvatarHasCorrectShape(SupportedLanguage language) {
    avatarPage.setRoute(language);
    var shapes = AvatarShape.values();
    for (int i = 0; i < shapes.length; i++) {
      var avatar = avatarPage.getAvatar(i);
      avatar.getLocator().assertThat().isVisible();
      avatar.getLocator().assertThat().hasAttribute("shape", shapes[i].name().toLowerCase());
    }
  }

}
