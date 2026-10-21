package com.webforj.samples.views.viewtransitions;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ViewTransitionChatViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ViewTransitionChatView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
