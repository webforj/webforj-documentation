package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class ScatterChart {
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.SCATTER);

  public ScatterChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Scatter Chart",
        "hAxis", Map.of(TITLE, "Age"),
        "vAxis", Map.of(TITLE, "Weight"),
        "colors", List.of("#FFA500"),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Age", "Weight"),
        List.of(8, 12),
        List.of(4, 5.5),
        List.of(11, 14),
        List.of(4, 5),
        List.of(3, 3.5),
        List.of(6.5, 7)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
