package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class SankeyChart {
  private static final String BRAZIL = "Brazil";
  private static final String CANADA = "Canada";
  
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.SANKEY);

  public SankeyChart() {
    Map<String, Object> options = Map.of(
        "title", "Flow of Resources",
        "backgroundColor", "transparent",
        "sankey", Map.of(
            "link", Map.of(
                "color", Map.of(
                    "fill", "#e6e6e6",
                    "fillOpacity", 0.3
                )
            )
        )
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("From", "To", "Weight"),
        List.of(BRAZIL, "Portugal", 5),
        List.of(BRAZIL, "France", 1),
        List.of(BRAZIL, "Spain", 1),
        List.of(CANADA, "Portugal", 1),
        List.of(CANADA, "France", 5),
        List.of(CANADA, "England", 1)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}

