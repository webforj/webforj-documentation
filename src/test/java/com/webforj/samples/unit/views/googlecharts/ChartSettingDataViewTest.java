package com.webforj.samples.unit.views.googlecharts;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.samples.views.googlecharts.ChartSettingDataView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ChartSettingDataViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ChartSettingDataView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldTakeAHeaderRowFollowedByOneRowPerRegionAsData(WebforjTestbed context) {
    context.add(new ChartSettingDataView());

    assertEquals(
        List.of(
            List.of("Region", "Sales"),
            List.of("North America", 500),
            List.of("Europe", 300),
            List.of("Asia", 200),
            List.of("Latin America", 100),
            List.of("Middle East", 80),
            List.of("Africa", 60)),
        ComponentLocator.of(GoogleChart.class).locate().getData());
  }
}
