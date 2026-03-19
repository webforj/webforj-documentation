package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginCustomFieldsPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LoginCustomFieldsViewIT extends BaseTest {

  private LoginCustomFieldsPage login;

  public void setupLoginCustomFields(SupportedLanguage language) {
    navigateToRoute(LoginCustomFieldsPage.getRoute(language));
    login = new LoginCustomFieldsPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSuccessfulLoginWithValidCredentials(SupportedLanguage language) {
    setupLoginCustomFields(language);
    login.getCustomderID().fill("Tesla");
    login.getUsername().fill("admin");
    login.getPassword().fill("admin");
    login.getSignInButton().click();

    assertThat(login.getLogoutButton()).isVisible();
    login.getLogoutButton().click();

    assertThat(login.getUsername()).isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInvalidCustomerIDIgnored(SupportedLanguage language) {
    setupLoginCustomFields(language);

    login.getCustomderID().fill("Toyota");
    login.getUsername().fill("admin");
    login.getPassword().fill("admin");
    login.getSignInButton().click();

    assertThat(login.getLogoutButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testValidCustomerIDAndBlankInputsIgnored(SupportedLanguage language) {
    setupLoginCustomFields(language);
    login.getCustomderID().fill("Tesla");
    login.getUsername().fill(" ");
    login.getPassword().fill(" ");
    login.getSignInButton().click();

    assertThat(login.getLogoutButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testValidCustomerIDAndInvalidInputsIgnored(SupportedLanguage language) {
    setupLoginCustomFields(language);
    login.getCustomderID().fill("Tesla");
    login.getUsername().fill("user1");
    login.getPassword().fill("wrongpass");
    login.getSignInButton().click();

    assertThat(login.getLogoutButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testBlankCustomerIDAndValidInputsIgnored(SupportedLanguage language) {
    setupLoginCustomFields(language);
    login.getCustomderID().fill(" ");
    login.getUsername().fill("admin");
    login.getPassword().fill("admin");
    login.getSignInButton().click();

    assertThat(login.getLogoutButton()).not().isVisible();
  }
}
