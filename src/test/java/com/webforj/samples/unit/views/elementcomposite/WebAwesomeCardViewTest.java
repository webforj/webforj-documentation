package com.webforj.samples.unit.views.elementcomposite;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.elementcomposite.WebAwesomeCardView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class WebAwesomeCardViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new WebAwesomeCardView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
