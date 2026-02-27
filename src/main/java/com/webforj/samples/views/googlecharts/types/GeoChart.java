package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class GeoChart {
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.GEO);

  public GeoChart() {
    Map<String, Object> options = Map.of(
        "title", "Geo Chart",
        "colorAxis", Map.of("colors", List.of("#4285f4", "#ab48bc")),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Country", "Popularity"),
        List.of("Germany", 200),
        List.of("United States", 300),
        List.of("Brazil", 400),
        List.of("Canada", 500),
        List.of("France", 600)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
