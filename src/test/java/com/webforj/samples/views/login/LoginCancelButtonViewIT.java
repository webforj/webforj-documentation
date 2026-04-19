package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginCancelButtonPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LoginCancelButtonViewIT extends BaseTest {

  private LoginCancelButtonPage login;

  public void setupLoginCancelButton(SupportedLanguage language) {
    navigateToRoute(LoginCancelButtonPage.getRoute(language));
    login = new LoginCancelButtonPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCancelButtonDisabled(SupportedLanguage language) {
    setupLoginCancelButton(language);
    login.getCancelButton().click();
    assertThat(login.getSignInButton()).hasAttribute("disabled", "");
  }
}
