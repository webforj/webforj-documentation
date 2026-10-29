package com.webforj.samples.unit.views.fields.maskedtimefield;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.MaskedTimeField;
import com.webforj.samples.views.fields.maskedtimefield.MaskedTimeFieldRestoreView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedTimeFieldRestoreViewTest {
  @Test
  void shouldRestoreTheConfiguredValue(WebforjTestbed context) {
    context.add(new MaskedTimeFieldRestoreView());

    var field = ComponentLocator.of(MaskedTimeField.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).locate());
    assertEquals(LocalTime.of(14, 0), field.getValue());
  }
}
