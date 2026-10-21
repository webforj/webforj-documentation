package com.webforj.samples.views.viewtransitions;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ViewTransitionShuffleViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ViewTransitionShuffleView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
