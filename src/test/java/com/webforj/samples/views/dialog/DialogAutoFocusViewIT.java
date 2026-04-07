package com.webforj.samples.views.dialog;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webforj.samples.pages.dialog.DialogAutoFocusPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class DialogAutoFocusViewIT extends BaseTest {

  private DialogAutoFocusPage dialogAutoFocusPage;

  public void setupDialogAutoFocus(SupportedLanguage language) {
    navigateToRoute(DialogAutoFocusPage.getRoute(language));
    dialogAutoFocusPage = new DialogAutoFocusPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testAutoFocusIsEnabled(SupportedLanguage language) {
    setupDialogAutoFocus(language);
    assertThat(dialogAutoFocusPage.getTextField()).isFocused();
  }

}
