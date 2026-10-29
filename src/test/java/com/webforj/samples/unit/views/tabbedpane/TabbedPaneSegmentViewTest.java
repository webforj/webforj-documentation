package com.webforj.samples.unit.views.tabbedpane;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.samples.views.tabbedpane.TabbedPaneSegmentView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TabbedPaneSegmentViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TabbedPaneSegmentView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldRenderTheTabsAsSegmentControl(WebforjTestbed context) {
    context.add(new TabbedPaneSegmentView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();

    assertTrue(pane.isSegment());
  }
}
