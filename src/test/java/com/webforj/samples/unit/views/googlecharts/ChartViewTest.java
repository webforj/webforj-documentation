package com.webforj.samples.unit.views.googlecharts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.samples.views.googlecharts.ChartView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class ChartViewTest {
  @Test
  void shouldMapARevenueForEachCountryOnAGeoChart(WebforjTestbed context) {
    context.add(new ChartView());

    var chart = ComponentLocator.of(GoogleChart.class).locate();
    var rows = chart.getData().subList(1, chart.getData().size());

    assertEquals(GoogleChart.Type.GEO, chart.getType());
    assertEquals(List.of("Country", "Revenue"), chart.getData().get(0));
    assertEquals(
        List.of(
            "Germany",
            "United States",
            "Brazil",
            "Canada",
            "France",
            "RU",
            "Australia",
            "South Africa",
            "China",
            "Egypt"),
        rows.stream().map(row -> ((List<?>) row).get(0)).toList());
    assertTrue(
        rows.stream()
            .map(row -> ((Number) ((List<?>) row).get(1)).doubleValue())
            .allMatch(revenue -> revenue >= 0 && revenue < 10000));
  }

  @Test
  void shouldStyleTheChartWithCustomColorsLegendAndChartArea(WebforjTestbed context) {
    context.add(new ChartView());

    var options = ComponentLocator.of(GoogleChart.class).locate().getOptions();

    assertEquals(List.of("#006fe6", "#8f64e0", "#ce55ca", "#fa49ab"), options.get("colors"));
    assertEquals(Map.of("width", "70%", "height", "80%"), options.get("chartArea"));
    assertEquals("top", ((Map<?, ?>) options.get("legend")).get("position"));
    assertEquals("center", ((Map<?, ?>) options.get("legend")).get("alignment"));
  }
}
