package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class ComboChart {
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.COMBO);

  public ComboChart() {
    
    Map<String, Object> options = Map.of(
        TITLE, "Revenue vs. Expenses",
        "vAxes", Map.of(
            "0", Map.of(TITLE, "Revenue", "minValue", 0),
            "1", Map.of(TITLE, "Expenses")
        ),
        "hAxis", Map.of(
            TITLE, "Month",
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        ),
        "backgroundColor", "transparent",
        "series", Map.of(
            "0", Map.of("type", "bars", "targetAxisIndex", 0),
            "1", Map.of("type", "line", "targetAxisIndex", 1, "color", "#e2431e")
        )
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Month", "Revenue", "Expenses"),
        List.of("January", 800, 500),
        List.of("February", 900, 700),
        List.of("March", 1000, 600),
        List.of("April", 1100, 900)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
