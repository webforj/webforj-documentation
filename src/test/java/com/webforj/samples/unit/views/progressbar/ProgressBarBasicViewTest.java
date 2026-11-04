package com.webforj.samples.unit.views.progressbar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.samples.views.progressbar.ProgressBarBasicView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ProgressBarBasicViewTest {
  @Test
  void shouldFillTheStripedAnimatedBarWhileStarted(WebforjTestbed context) {
    context.add(new ProgressBarBasicView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var pause = ComponentLocator.of(Button.class).byText("Pause").locate();

    assertTrue(bar.isStriped());
    assertTrue(bar.isAnimated());
    assertFalse(pause.isEnabled());

    UserInteraction.click(start);
    context.runIntervals();
    context.runIntervals();

    assertEquals(2, bar.getValue());
    assertFalse(start.isEnabled());
    assertTrue(pause.isEnabled());
  }

  @Test
  void shouldHoldTheValueWhilePaused(WebforjTestbed context) {
    context.add(new ProgressBarBasicView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var pause = ComponentLocator.of(Button.class).byText("Pause").locate();

    UserInteraction.click(start);
    context.runIntervals();
    UserInteraction.click(pause);
    context.runIntervals();

    assertEquals(1, bar.getValue());
    assertFalse(bar.isAnimated());
    assertTrue(start.isEnabled());
    assertFalse(pause.isEnabled());
  }

  @Test
  void shouldRestartFromZeroWhenTheUserResets(WebforjTestbed context) {
    context.add(new ProgressBarBasicView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var pause = ComponentLocator.of(Button.class).byText("Pause").locate();
    var reset = ComponentLocator.of(Button.class).byText("Reset").locate();

    UserInteraction.click(start);
    context.runIntervals();
    context.runIntervals();
    UserInteraction.click(pause);
    UserInteraction.click(reset);

    assertEquals(0, bar.getValue());
    assertTrue(bar.isAnimated());

    context.runIntervals();

    assertEquals(1, bar.getValue());
  }

  @Test
  void shouldStopAndDisableTheControlsWhenTheBarIsFull(WebforjTestbed context) {
    context.add(new ProgressBarBasicView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var pause = ComponentLocator.of(Button.class).byText("Pause").locate();

    UserInteraction.click(start);

    for (int i = 0; i < bar.getMax(); i++) {
      context.runIntervals();
    }

    assertEquals(bar.getMax(), bar.getValue());
    assertFalse(start.isEnabled());
    assertFalse(pause.isEnabled());
    assertFalse(bar.isAnimated());

    context.runIntervals();

    assertEquals(bar.getMax(), bar.getValue());
  }
}
