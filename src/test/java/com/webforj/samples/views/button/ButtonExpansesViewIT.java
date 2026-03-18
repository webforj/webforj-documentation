package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.component.Expanse;
import com.webforj.samples.pages.button.ButtonExpansesPage;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonExpansesViewIT extends BaseTest {
  private ButtonExpansesPage buttonPage;

  @BeforeEach
  public void setupButtonExpanses() {
    buttonPage = new ButtonExpansesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testChoiceBoxIsVisible(SupportedLanguage language) {
    buttonPage.setRoute(language);
    buttonPage.getChoiceBox().assertThat().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testButtonIsVisible(SupportedLanguage language) {
    buttonPage.setRoute(language);
    buttonPage.getButton().assertThat().isVisible();
  }

  // Temporarily disabled
//  @ParameterizedTest
//  @MethodSource("provideRoutes")
  public void testButtonExpanseChangesWhenSelected(SupportedLanguage language) {
    buttonPage.setRoute(language);
    var button = buttonPage.getButton();

    // Select LARGE from the ChoiceBox
    buttonPage.selectExpanse("XSMALL");

    // Verify button text changed to XSMALL
    button.assertThat().hasText("XSMALL");

    // Verify button expanse attribute changed to large
    button.assertThat().hasExpanse(Expanse.XSMALL);
  }

}
