package com.webforj.samples.views.spinner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.spinner.Spinner;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class SpinnerSpeedDemoViewTest {
  @Test
  void shouldPauseTheSpinnerWhenPauseIsClicked(WebforjTestbed context) {
    context.add(new SpinnerSpeedDemoView());

    var spinner = ComponentLocator.of(Spinner.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Pause").locate());

    assertTrue(spinner.isPaused());
  }

  @ParameterizedTest
  @CsvSource({"Fast, 200", "Medium, 500", "Slow, 1000"})
  void shouldResumeAtTheSpeedOfTheClickedButton(String button, int speed, WebforjTestbed context) {
    context.add(new SpinnerSpeedDemoView());

    var spinner = ComponentLocator.of(Spinner.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Pause").locate());
    UserInteraction.click(ComponentLocator.of(Button.class).byText(button).locate());

    assertFalse(spinner.isPaused());
    assertEquals(speed, spinner.getSpeed());
  }
}
