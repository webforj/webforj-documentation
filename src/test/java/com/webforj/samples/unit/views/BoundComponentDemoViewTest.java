package com.webforj.samples.unit.views;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.BoundComponentDemoView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class BoundComponentDemoViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new BoundComponentDemoView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
