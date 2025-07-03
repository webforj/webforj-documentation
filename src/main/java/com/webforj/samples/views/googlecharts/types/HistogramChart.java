package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class HistogramChart {

  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.HISTOGRAM);

  public HistogramChart() {
    Map<String, Object> options = new HashMap<>();
    options.put(TITLE, "Distribution of Weight");
    options.put("legend", Map.of("position", "none"));
    options.put("hAxis", Map.of(
        TITLE, "Weight",
        "minValue", 0,
        "textStyle", Map.of(
            "bold", true,
            "fontSize", 12,
            "color", "#4d4d4d"
        )
    ));
    options.put("vAxis", Map.of(
        TITLE, "Frequency",
        "minValue", 0,
        "textStyle", Map.of(
            "bold", true,
            "fontSize", 12,
            "color", "#4d4d4d"
        )
    ));
    options.put("colors", List.of("#4285f4"));
    options.put("backgroundColor", "transparent");
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Age"));
    data.add(Arrays.asList(18));
    data.add(Arrays.asList(20));
    data.add(Arrays.asList(22));
    data.add(Arrays.asList(24));
    data.add(Arrays.asList(26));
    data.add(Arrays.asList(28));
    data.add(Arrays.asList(30));
    chart.setData(data);

  }

  public GoogleChart getChart() {
    return chart;
  }
}
