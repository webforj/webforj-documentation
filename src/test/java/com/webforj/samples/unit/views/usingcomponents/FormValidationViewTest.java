package com.webforj.samples.unit.views.usingcomponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.usingcomponents.FormValidationView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FormValidationViewTest {
  @Test
  void shouldEnableSubmissionOnlyWhenEveryRulePasses(WebforjTestbed context) {
    context.add(new FormValidationView());

    var name =
        ComponentLocator.of(TextField.class).filter(f -> "Name".equals(f.getLabel())).locate();
    var email =
        ComponentLocator.of(TextField.class).filter(f -> "Email".equals(f.getLabel())).locate();
    var message = ComponentLocator.of(TextArea.class).locate();
    var submit = ComponentLocator.of(Button.class).byText("Send Message").locate();

    assertFalse(submit.isEnabled());

    UserInteraction.type(name, "Ada");
    UserInteraction.type(email, "invalid");
    UserInteraction.type(message, "0123456789");

    assertFalse(submit.isEnabled());

    UserInteraction.type(email, "ada@example.com");
    UserInteraction.type(message, "012345678");

    assertFalse(submit.isEnabled());

    UserInteraction.type(message, "0123456789");
    assertTrue(submit.isEnabled());

    UserInteraction.click(submit);
    assertEquals("Message sent from Ada!", ComponentLocator.of(Toast.class).locate().getText());

    UserInteraction.type(name, "");
    assertFalse(submit.isEnabled());
  }
}
