package com.webforj.samples.views.usingcomponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.toast.Toast;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ConditionalStateViewTest {
  @Test
  void shouldRequireBothTrimmedCredentialsAndShowTheUsername(WebforjTestbed context) {
    context.add(new ConditionalStateView());

    var username = ComponentLocator.of(TextField.class).locate();
    var password = ComponentLocator.of(PasswordField.class).locate();
    var submit = ComponentLocator.of(Button.class).byText("Sign In").locate();

    assertFalse(submit.isEnabled());

    UserInteraction.type(username, "Ada");
    UserInteraction.type(password, "   ");

    assertFalse(submit.isEnabled());

    UserInteraction.type(password, "secret");
    assertTrue(submit.isEnabled());

    UserInteraction.click(submit);
    assertEquals("Signed in as Ada", ComponentLocator.of(Toast.class).locate().getText());

    UserInteraction.type(username, "");
    assertFalse(submit.isEnabled());
  }
}
