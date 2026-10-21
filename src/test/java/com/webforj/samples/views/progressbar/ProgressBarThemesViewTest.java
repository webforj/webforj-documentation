package com.webforj.samples.views.progressbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ProgressBarThemesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ProgressBarThemesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowOneBarForEachBuiltInTheme(WebforjTestbed context) {
    context.add(new ProgressBarThemesView());

    var bars = ComponentLocator.of(ProgressBar.class).all();

    assertEquals(List.of(Theme.values()), bars.stream().map(ProgressBar::getTheme).toList());
  }
}
