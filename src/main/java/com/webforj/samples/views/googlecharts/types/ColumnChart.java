package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class ColumnChart {
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.COLUMN);

  public ColumnChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Annual Sales",
        "hAxis", Map.of(
            TITLE, "Year",
            "minValue", 0,
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12
            )
        ),
        "vAxis", Map.of(
            TITLE, "Sales (in USD)",
            "minValue", 0,
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12
            )
        ),
        "colors", List.of("#008000", "#4285f4"),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Year", "Sales", "Expenses"),
        List.of("2014", 1000, 400),
        List.of("2015", 1170, 460),
        List.of("2016", 660, 1120),
        List.of("2017", 1030, 540)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
