package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class AreaChart {
  private static final String TITLE = "title";
  private static final String TEXT_STYLE = "textStyle";
  private static final String COLOR = "color";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.AREA);

  public AreaChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Market Trends",
        "colors", List.of("#34a853", "#4285f4", "#fbbc05", "#ea4335"),
        "backgroundColor", "transparent",
        "hAxis", Map.of(
            TITLE, "Year",
            TEXT_STYLE, Map.of(
                "bold", true,
                "fontSize", 12,
                COLOR, "#4d4d4d"
            )
        ),
        "vAxis", Map.of(
            TITLE, "Value",
            TEXT_STYLE, Map.of(
                "bold", true,
                "fontSize", 12,
                COLOR, "#4d4d4d"
            )
        ),
        "isStacked", true
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Year", "Sales", "Expenses"),
        List.of("2013", 1000, 400),
        List.of("2014", 1170, 460),
        List.of("2015", 660, 600),
        List.of("2016", 1030, 540)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
