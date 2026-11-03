package com.webforj.samples.unit.views.button;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.samples.views.button.ButtonDisableView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ButtonDisableViewTest {
  @Test
  void shouldEnableSubmitOnlyWhileEmailContainsAtSign(WebforjTestbed context) {
    context.add(new ButtonDisableView());

    var email = ComponentLocator.of(TextField.class).locate();
    var submit = ComponentLocator.of(Button.class).byText("Submit").locate();

    assertFalse(submit.isEnabled());

    UserInteraction.type(email, "valid@example.com");
    assertTrue(submit.isEnabled());

    UserInteraction.type(email, "invalid-email");
    assertFalse(submit.isEnabled());
  }
}
