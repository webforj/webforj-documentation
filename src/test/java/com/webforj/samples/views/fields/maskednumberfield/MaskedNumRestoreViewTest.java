package com.webforj.samples.views.fields.maskednumberfield;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedNumRestoreViewTest {
  @Test
  void shouldRestoreTheConfiguredValue(WebforjTestbed context) {
    context.add(new MaskedNumRestoreView());

    var field = ComponentLocator.of(MaskedNumberField.class).locate();

    UserInteraction.type(field, "42");
    UserInteraction.click(ComponentLocator.of(Button.class).locate());

    assertEquals(1234567d, field.getValue());
  }
}
