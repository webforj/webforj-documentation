package com.webforj.samples.unit.views.progressbar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.samples.views.progressbar.ProgressBarOrientationView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ProgressBarOrientationViewTest {
  @Test
  void shouldShowTheBarVertically(WebforjTestbed context) {
    context.add(new ProgressBarOrientationView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();

    assertEquals(ProgressBar.Orientation.VERTICAL, bar.getOrientation());
  }

  @Test
  void shouldChangeTheThemeAsTheValueRisesAndAfterReset(WebforjTestbed context) {
    context.add(new ProgressBarOrientationView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Start").locate());
    context.runIntervals();

    assertEquals(26, bar.getValue());
    assertEquals(Theme.WARNING, bar.getTheme());

    for (int i = 0; i < 49; i++) {
      context.runIntervals();
    }

    assertEquals(75, bar.getValue());
    assertEquals(Theme.DANGER, bar.getTheme());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Reset").locate());

    assertEquals(0, bar.getValue());
    assertEquals(Theme.SUCCESS, bar.getTheme());
  }

  @Test
  void shouldHoldTheValueWhilePaused(WebforjTestbed context) {
    context.add(new ProgressBarOrientationView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var pause = ComponentLocator.of(Button.class).byText("Pause").locate();

    UserInteraction.click(start);
    context.runIntervals();
    UserInteraction.click(pause);
    context.runIntervals();

    assertEquals(26, bar.getValue());
    assertFalse(bar.isAnimated());
    assertTrue(start.isEnabled());
    assertFalse(pause.isEnabled());
  }

  @Test
  void shouldStopAndDisableTheControlsWhenTheBarIsFull(WebforjTestbed context) {
    context.add(new ProgressBarOrientationView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var pause = ComponentLocator.of(Button.class).byText("Pause").locate();
    var reset = ComponentLocator.of(Button.class).byText("Reset").locate();

    UserInteraction.click(start);

    for (int i = 25; i < bar.getMax(); i++) {
      context.runIntervals();
    }

    assertEquals(100, bar.getValue());
    assertEquals(Theme.DANGER, bar.getTheme());
    assertFalse(start.isEnabled());
    assertFalse(pause.isEnabled());
    assertTrue(reset.isEnabled());
    assertFalse(bar.isAnimated());
    assertEquals(0, context.runIntervals());
  }
}
