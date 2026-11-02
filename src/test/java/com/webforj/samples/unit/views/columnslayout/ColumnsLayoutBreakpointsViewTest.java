package com.webforj.samples.unit.views.columnslayout;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.samples.views.columnslayout.ColumnsLayoutBreakpointsView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ColumnsLayoutBreakpointsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ColumnsLayoutBreakpointsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldGrowFromOneToThreeColumnsThroughTheBreakpointsGivenAtConstruction(
      WebforjTestbed context) {
    context.add(new ColumnsLayoutBreakpointsView());

    var columns = ComponentLocator.of(ColumnsLayout.class).locate();

    assertEquals(
        List.of(
            new ColumnsLayout.Breakpoint("default", 0, 1),
            new ColumnsLayout.Breakpoint("small", "20em", 1),
            new ColumnsLayout.Breakpoint("medium", "40em", 2),
            new ColumnsLayout.Breakpoint("large", "60em", 3)),
        columns.getBreakpoints());
  }
}
