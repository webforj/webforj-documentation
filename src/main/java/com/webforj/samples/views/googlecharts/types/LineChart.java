package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class LineChart {
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.LINE);

  public LineChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Annual Growth",
        "backgroundColor", "transparent",
        "hAxis", Map.of(
            TITLE, "Year",
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        )
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Year", "Sales", "Expenses"),
        List.of("2004", 1000, 400),
        List.of("2005", 1170, 460),
        List.of("2006", 660, 1120),
        List.of("2007", 1030, 540)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
