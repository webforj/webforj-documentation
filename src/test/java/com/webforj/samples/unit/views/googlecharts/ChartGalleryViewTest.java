package com.webforj.samples.unit.views.googlecharts;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Anchor;
import com.webforj.samples.views.googlecharts.ChartGalleryView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ChartGalleryViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ChartGalleryView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowEachOfTheTwentyOneChartTypesOnce(WebforjTestbed context) {
    context.add(new ChartGalleryView());

    var charts = ComponentLocator.of(GoogleChart.class).all();

    assertEquals(21, charts.size());
    assertEquals(21, charts.stream().map(GoogleChart::getType).distinct().count());
  }

  @Test
  void shouldOpenTheSourceOfEachChartInANewTab(WebforjTestbed context) {
    context.add(new ChartGalleryView());

    assertTrue(
        ComponentLocator.of(Anchor.class).all().stream()
            .allMatch(link -> "_blank".equals(link.getTarget())));
  }
}
