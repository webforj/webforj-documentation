package com.webforj.samples.unit.views.markdownviewer;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.samples.views.markdownviewer.MarkdownViewerView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MarkdownViewerViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new MarkdownViewerView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldRenderTheMarkdownSetAsContent(WebforjTestbed context) {
    context.add(new MarkdownViewerView());

    var viewer = ComponentLocator.of(MarkdownViewer.class).locate();

    assertTrue(viewer.getContent().startsWith("# Welcome to MarkdownViewer\n"));
    assertTrue(viewer.getContent().contains("```java\nMarkdownViewer viewer"));
  }
}
