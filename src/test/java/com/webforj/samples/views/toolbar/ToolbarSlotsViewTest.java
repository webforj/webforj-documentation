package com.webforj.samples.views.toolbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ToolbarSlotsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ToolbarSlotsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
