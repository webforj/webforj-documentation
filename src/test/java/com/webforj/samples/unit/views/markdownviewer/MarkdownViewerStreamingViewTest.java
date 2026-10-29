package com.webforj.samples.unit.views.markdownviewer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.markdown.MarkdownViewerInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.IconButton;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.samples.views.markdownviewer.MarkdownViewerStreamingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MarkdownViewerStreamingViewTest {
  @Test
  void shouldAppendTheMessageAndStreamTheResponseIntoTheViewer(WebforjTestbed context) {
    context.add(new MarkdownViewerStreamingView());

    var input = ComponentLocator.of(TextField.class).locate();
    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();

    UserInteraction.type(input, "Hello");
    UserInteraction.keypress(input, KeypressEvent.Key.ENTER);

    assertTrue(viewer.getContent().contains(">Hello</p>"));
    assertEquals("", input.getText());
    assertTrue(ComponentLocator.of(Span.class).byText("Thinking...").isPresent());

    context.runIntervals();

    assertFalse(ComponentLocator.of(Span.class).byText("Thinking...").isPresent());

    String before = viewer.getContent();
    context.runIntervals();

    assertTrue(viewer.getContent().startsWith(before));
    assertTrue(viewer.getContent().length() > before.length());
    assertTrue(viewer.isRendering());
  }

  @Test
  void shouldShowSendAgainOnlyWhenTheRenderingCompletes(WebforjTestbed context) {
    context.add(new MarkdownViewerStreamingView());

    var input = ComponentLocator.of(TextField.class).locate();
    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();
    var send =
        ComponentLocator.of(IconButton.class)
            .filter(button -> button.getTheme() != Theme.DANGER)
            .locate();
    var stop =
        ComponentLocator.of(IconButton.class)
            .filter(button -> button.getTheme() == Theme.DANGER)
            .locate();

    UserInteraction.type(input, "Tell me more");
    UserInteraction.click(send);

    int remaining = 1000;
    while (remaining > 0 && context.runIntervals() > 0) {
      remaining--;
    }

    assertTrue(remaining > 0, "The response should finish streaming");
    assertFalse(send.isVisible());
    assertTrue(stop.isVisible());

    UserInteraction.perform(viewer, MarkdownViewerInteraction.completeRendering());

    assertTrue(send.isVisible());
    assertFalse(stop.isVisible());
    assertTrue(input.hasFocus());
  }

  @Test
  void shouldStopTheResponseWhenTheUserStops(WebforjTestbed context) {
    context.add(new MarkdownViewerStreamingView());

    var input = ComponentLocator.of(TextField.class).locate();
    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();
    var send =
        ComponentLocator.of(IconButton.class)
            .filter(button -> button.getTheme() != Theme.DANGER)
            .locate();
    var stop =
        ComponentLocator.of(IconButton.class)
            .filter(button -> button.getTheme() == Theme.DANGER)
            .locate();

    UserInteraction.type(input, "Hello");
    UserInteraction.keypress(input, KeypressEvent.Key.ENTER);
    context.runIntervals();
    context.runIntervals();

    UserInteraction.click(stop);
    String stopped = viewer.getContent();
    context.runIntervals();

    assertEquals(stopped, viewer.getContent());
    assertFalse(viewer.isRendering());
    assertFalse(stop.isVisible());
    assertTrue(send.isVisible());
  }

  @Test
  void shouldIgnoreBlankMessages(WebforjTestbed context) {
    context.add(new MarkdownViewerStreamingView());

    var input = ComponentLocator.of(TextField.class).locate();

    UserInteraction.type(input, "   ");
    UserInteraction.keypress(input, KeypressEvent.Key.ENTER);
    context.runIntervals();

    assertEquals("", ComponentLocator.of(MarkdownViewer.class).locate().getContent());
  }
}
