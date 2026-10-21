package com.webforj.samples.views.splitter;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SplitterOrientationViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SplitterOrientationView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldStackTheMasterAboveTheDetail(WebforjTestbed context) {
    context.add(new SplitterOrientationView());

    var splitter = ComponentLocator.of(Splitter.class).locate();

    assertEquals(Splitter.Orientation.VERTICAL, splitter.getOrientation());
  }
}
