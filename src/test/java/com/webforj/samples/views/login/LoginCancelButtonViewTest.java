package com.webforj.samples.views.login;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.login.Login;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class LoginCancelButtonViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new LoginCancelButtonView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldLetTheUserCancelOnceTheCancelButtonHasALabel(WebforjTestbed context) {
    context.add(new LoginCancelButtonView());

    var login = ComponentLocator.of(Login.class).locate();

    assertDoesNotThrow(() -> UserInteraction.cancel(login));
    assertTrue(login.isOpened());
    assertFalse(login.isEnabled());
  }
}
