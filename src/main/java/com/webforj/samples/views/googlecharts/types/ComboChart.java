package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboChart {

  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.COMBO);

  public ComboChart() {

    Map<String, Object> options = new HashMap<>();
    options.put(TITLE, "Revenue vs. Expenses");
    options.put("vAxes", Map.of(
        "0", Map.of(TITLE, "Revenue", "minValue", 0),
        "1", Map.of(TITLE, "Expenses")
    ));
    options.put("hAxis", Map.of(
        TITLE, "Month",
        "textStyle", Map.of(
            "bold", true,
            "fontSize", 12,
            "color", "#4d4d4d"
        )
    ));
    options.put("backgroundColor", "transparent");
    options.put("series", Map.of(
        "0", Map.of("type", "bars", "targetAxisIndex", 0),
        "1", Map.of("type", "line", "targetAxisIndex", 1, "color", "#e2431e")
    ));
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Month", "Revenue", "Expenses"));
    data.add(Arrays.asList("January", 800, 500));
    data.add(Arrays.asList("February", 900, 700));
    data.add(Arrays.asList("March", 1000, 600));
    data.add(Arrays.asList("April", 1100, 900));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
