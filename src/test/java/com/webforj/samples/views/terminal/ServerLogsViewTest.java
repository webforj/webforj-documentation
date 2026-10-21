package com.webforj.samples.views.terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ServerLogsViewTest {
  @Test
  void shouldStreamLogsAfterTheUserStartsTheStream(WebforjTestbed context) {
    context.add(new ServerLogsView());

    var button = ComponentLocator.of(Button.class).locate();

    UserInteraction.click(button);

    assertEquals("Stop Log Stream", button.getText());
    assertEquals(1, context.runIntervals());
  }

  @Test
  void shouldStopStreamingLogsWhenTheUserStopsTheStream(WebforjTestbed context) {
    context.add(new ServerLogsView());

    var button = ComponentLocator.of(Button.class).locate();

    UserInteraction.click(button);
    UserInteraction.click(button);

    assertEquals("Start Log Stream", button.getText());
    assertEquals(0, context.runIntervals());
  }

  @Test
  void shouldPauseTheStreamOnceMoreThan500LinesWaitForTheTerminal(WebforjTestbed context) {
    context.add(new ServerLogsView());

    UserInteraction.click(ComponentLocator.of(Button.class).locate());

    var written = 0;
    for (int tick = 0; tick < 600; tick++) {
      written += context.runIntervals();
    }

    assertEquals(501, written);
  }
}
