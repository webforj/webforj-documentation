package com.webforj.samples.views.button;

import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.pages.button.ButtonEventPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonEventViewIT extends BaseTest {

  private ButtonEventPage buttonEventPage;

  @BeforeEach
  public void setupButtonEventDemo() {
    buttonEventPage = new ButtonEventPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testButtonEventIsTriggeredWhenButtonIsClicked(SupportedLanguage language) {
    buttonEventPage.setRoute(language);
    buttonEventPage.getButton().click();
    buttonEventPage.getCounterText("1").assertThat().isVisible();
  }

}
