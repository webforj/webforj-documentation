package com.webforj.samples.views.radiobutton;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class RadioButtonSwitchViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new RadioButtonSwitchView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheFactoryButtonAsASwitchBesideANormalButton(WebforjTestbed context) {
    context.add(new RadioButtonSwitchView());

    var buttons = ComponentLocator.of(RadioButton.class).all();

    assertEquals(List.of(false, true), buttons.stream().map(RadioButton::isSwitch).toList());
  }

  @Test
  void shouldCheckTheSwitchLikeARadioButtonWhenTheUserChecksIt(WebforjTestbed context) {
    context.add(new RadioButtonSwitchView());

    var toggle = ComponentLocator.of(RadioButton.class).byText("Switch RadioButton").locate();

    UserInteraction.check(toggle);

    assertTrue(toggle.isChecked());
  }
}
