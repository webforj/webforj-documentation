package com.webforj.samples.views.fields.maskednumberfield;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedNumNegatableViewTest {
  @Test
  void shouldAllowNegativeCreditsOnlyWhileTheSwitchIsOn(WebforjTestbed context) {
    context.add(new MaskedNumNegatableView());

    var field = ComponentLocator.of(MaskedNumberField.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();

    assertTrue(field.isNegateable());

    UserInteraction.uncheck(toggle);
    assertFalse(field.isNegateable());

    UserInteraction.check(toggle);
    assertTrue(field.isNegateable());
  }
}
