package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class BarChart {
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

  public BarChart() {

    Map<String, Object> options = Map.of(
        TITLE, "Comparison of Quarterly Revenue",
        "bars", "horizontal",
        "hAxis", Map.of(
            TITLE, "Total Revenue",
            "minValue", 0,
            "format", "currency",
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        ),
        "vAxis", Map.of(
            TITLE, "Quarter",
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        ),
        "colors", List.of("#e0440e"),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Quarter", "Revenue"),
        List.of("Q1", 10000),
        List.of("Q2", 11700),
        List.of("Q3", 6600),
        List.of("Q4", 10300)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
