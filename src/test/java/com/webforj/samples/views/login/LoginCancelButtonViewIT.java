package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.login.LoginCancelButtonPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginCancelButtonViewIT extends BaseTest {

  private LoginCancelButtonPage login;

  @BeforeEach
  public void setupLoginCancelButton() {
    navigateToRoute(LoginCancelButtonPage.getRoute());
    login = new LoginCancelButtonPage(page);
  }

  @Test
  public void testCancelButtonDisabled() {
    login.getCancelButton().click();
    assertThat(login.getSignInButton()).hasAttribute("disabled", "");
  }
}
