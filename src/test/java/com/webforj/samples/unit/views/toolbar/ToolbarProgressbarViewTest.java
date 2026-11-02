package com.webforj.samples.unit.views.toolbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.samples.views.toolbar.ToolbarProgressbarView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ToolbarProgressbarViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ToolbarProgressbarView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldPlaceTheProgressBarInsideTheToolbar(WebforjTestbed context) {
    context.add(new ToolbarProgressbarView());

    var toolbar = ComponentLocator.of(Toolbar.class).locate();
    var progressBar = ComponentLocator.of(ProgressBar.class).locate();

    verifyThat(toolbar).hasComponent(progressBar);
  }
}
