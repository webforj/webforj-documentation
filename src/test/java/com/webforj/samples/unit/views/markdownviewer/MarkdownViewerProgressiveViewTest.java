package com.webforj.samples.unit.views.markdownviewer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.markdown.MarkdownViewerInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.samples.views.markdownviewer.MarkdownViewerProgressiveView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@WebforjTest
class MarkdownViewerProgressiveViewTest {
  @Test
  void shouldRenderTheContentProgressivelyWhenTheUserStarts(WebforjTestbed context) {
    context.add(new MarkdownViewerProgressiveView());

    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var stop = ComponentLocator.of(Button.class).byText("Stop").locate();

    assertFalse(stop.isEnabled());

    UserInteraction.click(start);

    assertTrue(viewer.isRendering());
    assertTrue(viewer.getContent().startsWith("# The Octopus"));
    assertFalse(start.isEnabled());
    assertTrue(stop.isEnabled());
  }

  @Test
  void shouldStopRenderingWhenTheUserStops(WebforjTestbed context) {
    context.add(new MarkdownViewerProgressiveView());

    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var stop = ComponentLocator.of(Button.class).byText("Stop").locate();

    UserInteraction.click(start);
    UserInteraction.click(stop);

    assertFalse(viewer.isRendering());
    assertTrue(start.isEnabled());
    assertFalse(stop.isEnabled());
  }

  @Test
  void shouldEnableStartAgainWhenTheRenderingCompletes(WebforjTestbed context) {
    context.add(new MarkdownViewerProgressiveView());

    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();
    var start = ComponentLocator.of(Button.class).byText("Start").locate();
    var stop = ComponentLocator.of(Button.class).byText("Stop").locate();

    UserInteraction.click(start);
    UserInteraction.perform(viewer, MarkdownViewerInteraction.completeRendering());

    assertFalse(viewer.isRendering());
    assertTrue(start.isEnabled());
    assertFalse(stop.isEnabled());
  }

  @ParameterizedTest
  @ValueSource(ints = {2, 6, 10})
  void shouldRenderAtTheSpeedTheUserPicks(int speed, WebforjTestbed context) {
    context.add(new MarkdownViewerProgressiveView());

    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();
    var speedChoice = ComponentLocator.of(ChoiceBox.class).locate();

    UserInteraction.selectKey(speedChoice, String.valueOf(speed));

    assertEquals(speed, viewer.getRenderSpeed());
  }
}
