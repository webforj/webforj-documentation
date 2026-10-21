package com.webforj.samples.views.composite;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AnalyticsCardCompositeViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AnalyticsCardCompositeView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
