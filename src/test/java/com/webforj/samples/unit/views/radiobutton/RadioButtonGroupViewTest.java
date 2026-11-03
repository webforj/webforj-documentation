package com.webforj.samples.unit.views.radiobutton;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.radiobutton.RadioButtonGroupView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class RadioButtonGroupViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new RadioButtonGroupView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldUncheckThePreviousAnswerWhenTheUserChecksAnother(WebforjTestbed context) {
    context.add(new RadioButtonGroupView());

    var stronglyDisagree =
        ComponentLocator.of(RadioButton.class).byText("Strongly Disagree").locate();
    var disagree = ComponentLocator.of(RadioButton.class).byText("Disagree").locate();

    UserInteraction.check(stronglyDisagree);
    assertTrue(stronglyDisagree.isChecked());

    UserInteraction.check(disagree);

    assertTrue(disagree.isChecked());
    assertFalse(stronglyDisagree.isChecked());
  }
}
