package com.webforj.samples.unit.views.button;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.samples.views.button.ButtonEventView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ButtonEventViewTest {
  @Test
  void shouldIncrementCounterForEveryClick(WebforjTestbed context) {
    context.add(new ButtonEventView());

    var button = ComponentLocator.of(Button.class).locate();

    for (int i = 1; i <= 3; i++) {
      UserInteraction.click(button);
      assertTrue(ComponentLocator.of(Div.class).byText("Current Counter: " + i).isPresent());
    }

    assertTrue(ComponentLocator.of(Div.class).byTextContaining("Event payload:").isPresent());
  }
}
