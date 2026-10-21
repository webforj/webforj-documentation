package com.webforj.samples.views.login;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.login.LoginInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.login.Login;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class LoginSubmissionViewTest {
  @Test
  void shouldCloseTheLoginAndShowLogoutForTheAdminCredentials(WebforjTestbed context) {
    context.add(new LoginSubmissionView());

    var login = ComponentLocator.of(Login.class).locate();

    UserInteraction.perform(
        login, LoginInteraction.submit(Map.of("username", "admin", "password", "admin")));

    assertFalse(login.isOpened());
    assertTrue(ComponentLocator.of(Button.class).byText("Logout").isPresent());
  }

  @Test
  void shouldShowTheErrorAndEnableSignInAgainForWrongCredentials(WebforjTestbed context) {
    context.add(new LoginSubmissionView());

    var login = ComponentLocator.of(Login.class).locate();

    UserInteraction.perform(
        login, LoginInteraction.submit(Map.of("username", "admin", "password", "wrong")));

    assertTrue(login.isOpened());
    assertTrue(login.isError());
    assertTrue(login.isEnabled());
    assertFalse(ComponentLocator.of(Button.class).byText("Logout").isPresent());

    UserInteraction.perform(
        login, LoginInteraction.submit(Map.of("username", "admin", "password", "admin")));

    assertFalse(login.isOpened());
  }
}
