package com.webforj.samples.views.toolbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ToolbarCompactViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ToolbarCompactView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldCompactOnlyTheSecondaryNavigation(WebforjTestbed context) {
    context.add(new ToolbarCompactView());

    var bars = ComponentLocator.of(Toolbar.class).all();

    assertFalse(bars.get(0).isCompact());
    assertTrue(bars.get(1).isCompact());
  }
}
