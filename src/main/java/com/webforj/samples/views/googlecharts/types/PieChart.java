package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class PieChart {
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.PIE);

  public PieChart() {
    Map<String, Object> options = Map.of(
        "title", "Social Media Distribution",
        "is3D", true,
        "pieSliceText", "percentage",
        "colors", List.of("#34a853", "#4285f4", "#fbbc05", "#ea4335", "#ff5722"),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Task", "Hours per Day"),
        List.of("Work", 11),
        List.of("Eat", 2),
        List.of("Commute", 2),
        List.of("Watch TV", 2),
        List.of("Sleep", 7)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
