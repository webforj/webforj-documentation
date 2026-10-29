package com.webforj.samples.unit.views.spinner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.spinner.Spinner;
import com.webforj.samples.views.spinner.SpinnerDirectionDemoView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SpinnerDirectionDemoViewTest {
  @Test
  void shouldSpinCounterclockwiseAndBackClockwiseWithTheButtons(WebforjTestbed context) {
    context.add(new SpinnerDirectionDemoView());

    var spinner = ComponentLocator.of(Spinner.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Counterclockwise").locate());
    assertFalse(spinner.isClockwise());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Clockwise").locate());
    assertTrue(spinner.isClockwise());
  }
}
