package com.webforj.samples.unit.views.fields.passwordfield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.PasswordField;
import com.webforj.samples.views.fields.passwordfield.PasswordFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class PasswordFieldViewTest {
  @Test
  void shouldPromptForThePasswordWithALabelAndPlaceholder(WebforjTestbed context) {
    context.add(new PasswordFieldView());

    var password = ComponentLocator.of(PasswordField.class).locate();

    verifyThat(password).hasLabel("Enter your Password:").hasPlaceholder("Password");
  }

  @Test
  void shouldHoldThePasswordTheUserTypes(WebforjTestbed context) {
    context.add(new PasswordFieldView());

    var password = ComponentLocator.of(PasswordField.class).locate();

    UserInteraction.type(password, "Password123!");

    verifyThat(password).hasValue("Password123!");
  }
}
