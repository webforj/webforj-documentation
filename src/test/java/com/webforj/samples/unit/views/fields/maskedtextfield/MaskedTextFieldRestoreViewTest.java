package com.webforj.samples.unit.views.fields.maskedtextfield;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.MaskedTextField;
import com.webforj.samples.views.fields.maskedtextfield.MaskedTextFieldRestoreView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedTextFieldRestoreViewTest {
  @Test
  void shouldRestoreTheConfiguredValue(WebforjTestbed context) {
    context.add(new MaskedTextFieldRestoreView());

    var field = ComponentLocator.of(MaskedTextField.class).locate();

    UserInteraction.type(field, "90210 LAX");
    UserInteraction.click(ComponentLocator.of(Button.class).locate());

    assertEquals("85001 PHX", field.getValue());
  }
}
