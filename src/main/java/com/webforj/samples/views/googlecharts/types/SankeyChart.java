package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class SankeyChart {
  private static final String BRAZIL = "Brazil";
  private static final String CANADA = "Canada";
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.SANKEY);

  public SankeyChart() {
    Map<String, Object> options = new HashMap<>();
    options.put("title", "Flow of Resources");
    options.put("backgroundColor", "transparent");
    options.put("sankey", Map.of(
        "link", Map.of(
            "color", Map.of(
                "fill", "#e6e6e6",
                "fillOpacity", 0.3
            )
        )
    ));
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("From", "To", "Weight"));
    data.add(Arrays.asList(BRAZIL, "Portugal", 5));
    data.add(Arrays.asList(BRAZIL, "France", 1));
    data.add(Arrays.asList(BRAZIL, "Spain", 1));
    data.add(Arrays.asList(CANADA, "Portugal", 1));
    data.add(Arrays.asList(CANADA, "France", 5));
    data.add(Arrays.asList(CANADA, "England", 1));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}

