package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.utils.SupportedLanguage;

import com.webforj.samples.pages.button.ButtonPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ButtonViewIT extends BaseTest {

  private ButtonPage buttonPage;

  @BeforeEach
  public void setupButtonDemo() {
    buttonPage = new ButtonPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testWelcomeMessageIsDisplayedWhenSubmitButtonIsClicked(SupportedLanguage language) {
    buttonPage.setRoute(language);
    buttonPage.getSubmitButton().click();
    var dialog = buttonPage.getWelcomeDialog();
    dialog.assertThat().isVisible();
    buttonPage.getDialogContent().assertThat().hasText("Welcome to the app Jason Turner!");
    buttonPage.getOkButton().click();
    dialog.assertThat().not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInputsAreClearedWhenClearButtonIsClicked(SupportedLanguage language) {
    buttonPage.setRoute(language);
    buttonPage.getFirstName().assertThat().hasValue("Jason");
    buttonPage.getLastName().assertThat().hasValue("Turner");
    buttonPage.getEmail().assertThat().hasValue("turner.jason@email.com");

    buttonPage.getClearButton().click();

    buttonPage.getFirstName().assertThat().hasValue("");
    buttonPage.getLastName().assertThat().hasValue("");
    buttonPage.getEmail().assertThat().hasValue("");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testWelcomeContentChanges(SupportedLanguage language) {
    buttonPage.setRoute(language);
    var firstName = buttonPage.getFirstName();
    firstName.clear();
    firstName.fill("Test");
    var lastName = buttonPage.getLastName();
    lastName.clear();
    lastName.fill("123");
    buttonPage.getSubmitButton().click();
    buttonPage.getDialogContent().assertThat().hasText("Welcome to the app Test 123!");
  }
}
