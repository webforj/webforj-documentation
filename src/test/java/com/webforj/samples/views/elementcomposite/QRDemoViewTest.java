package com.webforj.samples.views.elementcomposite;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class QRDemoViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new QRDemoView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
