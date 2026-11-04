package com.webforj.samples.unit.views.login;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.login.Login;
import com.webforj.samples.views.login.LoginBasicView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class LoginBasicViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new LoginBasicView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheLoginDialogOnceOpened(WebforjTestbed context) {
    context.add(new LoginBasicView());

    var login = ComponentLocator.of(Login.class).locate();

    assertTrue(login.isOpened());
  }
}
