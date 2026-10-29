package com.webforj.samples.unit.views.elementcomposite;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.elementcomposite.QRDemoView;
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
