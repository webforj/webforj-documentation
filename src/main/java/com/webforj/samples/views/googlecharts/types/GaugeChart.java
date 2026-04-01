package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class GaugeChart {
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.GAUGE);

  public GaugeChart() {
    
    Map<String, Object> options = Map.of(
        TITLE, "Performance Metrics",
        "greenFrom", 75,
        "greenTo", 100,
        "yellowFrom", 50,
        "yellowTo", 75,
        "redFrom", 0,
        "redTo", 50,
        "minorTicks", 5,
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Label", "Value"),
        List.of("Memory", 80),
        List.of("CPU", 55),
        List.of("Network", 68)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
