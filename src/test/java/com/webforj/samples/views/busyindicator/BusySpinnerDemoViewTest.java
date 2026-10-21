package com.webforj.samples.views.busyindicator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.webforj.App;
import com.webforj.component.Theme;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class BusySpinnerDemoViewTest {
  @Test
  void shouldChangeTheSpinnerThemeSizeDirectionAndSpeedOnEachTick(WebforjTestbed context) {
    var view = new BusySpinnerDemoView();
    context.add(view);

    var indicator = App.getBusyIndicator();
    var themes =
        List.of(
            Theme.DEFAULT,
            Theme.DANGER,
            Theme.GRAY,
            Theme.INFO,
            Theme.PRIMARY,
            Theme.SUCCESS,
            Theme.WARNING);
    var expanses = List.of(SpinnerExpanse.SMALL, SpinnerExpanse.MEDIUM, SpinnerExpanse.LARGE);

    for (int i = 0; i < themes.size(); i++) {
      context.runIntervals();
      assertEquals(themes.get(i), indicator.getSpinner().getTheme());
      assertEquals(expanses.get(i % 3), indicator.getSpinner().getExpanse());
    }

    context.runIntervals();
    assertFalse(indicator.getSpinner().isClockwise());

    context.runIntervals();
    assertEquals(500, indicator.getSpinner().getSpeed());

    context.runIntervals();
    assertEquals("Demo complete!", indicator.getText());

    context.runIntervals();
    assertEquals("Demo complete!", indicator.getText());
  }
}
