package com.webforj.samples.views.button;

import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.button.ButtonIconPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonIconViewIT extends BaseTest {
  private ButtonIconPage buttonIconPage;

  @BeforeEach
  public void setupButtonIconDemo() {
    buttonIconPage = new ButtonIconPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAllButtonsWithIconsAreVisible(SupportedLanguage language) {
    buttonIconPage.setRoute(language);
    var notificationsButton = buttonIconPage.getButtons(0);
    notificationsButton.assertThat().isVisible();
    notificationsButton.assertThat().hasText("Notifications");

    var searchButton = buttonIconPage.getButtons(1);
    searchButton.assertThat().isVisible();
    searchButton.assertThat().hasText("Search");

    var imageButton = buttonIconPage.getButtons(2);
    imageButton.assertThat().isVisible();
  }

}
