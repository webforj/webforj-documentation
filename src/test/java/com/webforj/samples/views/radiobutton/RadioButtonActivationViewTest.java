package com.webforj.samples.views.radiobutton;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class RadioButtonActivationViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new RadioButtonActivationView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldActivateTheFirstPairAutomaticallyAndTheSecondManually(WebforjTestbed context) {
    context.add(new RadioButtonActivationView());

    var buttons = ComponentLocator.of(RadioButton.class).all();

    assertEquals(
        List.of(
            RadioButton.Activation.AUTO, RadioButton.Activation.AUTO,
            RadioButton.Activation.MANUAL, RadioButton.Activation.MANUAL),
        buttons.stream().map(RadioButton::getActivation).toList());
  }
}
