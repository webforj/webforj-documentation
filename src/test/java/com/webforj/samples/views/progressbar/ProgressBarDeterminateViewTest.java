package com.webforj.samples.views.progressbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ProgressBarDeterminateViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ProgressBarDeterminateView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowAnIndeterminateBarForATaskOfUnknownLength(WebforjTestbed context) {
    context.add(new ProgressBarDeterminateView());

    var bar = ComponentLocator.of(ProgressBar.class).locate();

    assertTrue(bar.isIndeterminate());
  }
}
