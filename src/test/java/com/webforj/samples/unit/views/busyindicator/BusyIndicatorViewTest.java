package com.webforj.samples.unit.views.busyindicator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.App;
import com.webforj.component.Theme;
import com.webforj.samples.views.busyindicator.BusyIndicatorView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class BusyIndicatorViewTest {
  @Test
  void shouldAdvanceRetriesAndStopAfterCompletion(WebforjTestbed context) {
    context.add(new BusyIndicatorView());

    var indicator = App.getBusyIndicator();

    assertTrue(indicator.getText().contains("3 seconds"));

    context.runIntervals();

    assertEquals(Theme.WARNING, indicator.getSpinner().getTheme());
    assertTrue(indicator.getText().startsWith("Attempt 1:"));

    context.runIntervals();

    assertEquals(Theme.DANGER, indicator.getSpinner().getTheme());
    assertTrue(indicator.getText().startsWith("Attempt 2:"));

    context.runIntervals();

    assertEquals(Theme.SUCCESS, indicator.getSpinner().getTheme());
    String completed = indicator.getText();
    assertTrue(completed.startsWith("Demo complete!"));

    context.runIntervals();
    assertEquals(completed, indicator.getText());
  }
}
