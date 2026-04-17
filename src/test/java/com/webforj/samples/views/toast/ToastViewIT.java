package com.webforj.samples.views.toast;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ToastViewIT extends BaseTest {


  public void setupToastView(SupportedLanguage language) {
    navigateToRoute(language.getPath("toast"));
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testToastStopButtonClosesToast(SupportedLanguage language) {
    setupToastView(language);
    Locator toast = page.getByText("System update failed. Restoring to the previous state.");
    assertThat(toast).isVisible();

    Locator stopButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Stop"));
    stopButton.click();

    assertThat(stopButton).not().isVisible();
  }
}
