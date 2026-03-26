package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class HistogramChart {
  private static final String TITLE = "title";
  
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.HISTOGRAM);

  public HistogramChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Distribution of Weight",
        "legend", Map.of("position", "none"),
        "hAxis", Map.of(
            TITLE, "Weight",
            "minValue", 0,
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        ),
        "vAxis", Map.of(
            TITLE, "Frequency",
            "minValue", 0,
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        ),
        "colors", List.of("#4285f4"),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Age"),
        List.of(18),
        List.of(20),
        List.of(22),
        List.of(24),
        List.of(26),
        List.of(28),
        List.of(30)
    );
    chart.setData(data);
    
  }

  public GoogleChart getChart() {
    return chart;
  }
}
