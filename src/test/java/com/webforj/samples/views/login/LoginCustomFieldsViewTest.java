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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class LoginCustomFieldsViewTest {
  @Test
  void shouldSignInWithTheCustomerIdReadFromTheSubmittedData(WebforjTestbed context) {
    context.add(new LoginCustomFieldsView());

    var login = ComponentLocator.of(Login.class).locate();

    UserInteraction.perform(
        login,
        LoginInteraction.submit(
            Map.of("username", "admin", "password", "admin", "customer-id", "Tesla")));

    assertFalse(login.isOpened());
    assertTrue(ComponentLocator.of(Button.class).byText("Logout").isPresent());
  }

  @ParameterizedTest
  @CsvSource({"Other, admin, admin", "Tesla, admin, wrong"})
  void shouldShowTheErrorAndEnableSignInAgainForAWrongCustomerOrCredentials(
      String customerId, String username, String password, WebforjTestbed context) {
    context.add(new LoginCustomFieldsView());

    var login = ComponentLocator.of(Login.class).locate();

    UserInteraction.perform(
        login,
        LoginInteraction.submit(
            Map.of("username", username, "password", password, "customer-id", customerId)));

    assertTrue(login.isError());
    assertTrue(login.isOpened());
    assertTrue(login.isEnabled());
    assertFalse(ComponentLocator.of(Button.class).byText("Logout").isPresent());
  }
}
