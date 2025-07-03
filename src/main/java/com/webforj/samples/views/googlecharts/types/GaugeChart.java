package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class GaugeChart {

  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.GAUGE);

  public GaugeChart() {

    Map<String, Object> options = new HashMap<>();
    options.put(TITLE, "Performance Metrics");
    options.put("greenFrom", 75);
    options.put("greenTo", 100);
    options.put("yellowFrom", 50);
    options.put("yellowTo", 75);
    options.put("redFrom", 0);
    options.put("redTo", 50);
    options.put("minorTicks", 5);
    options.put("backgroundColor", "transparent");
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Label", "Value"));
    data.add(Arrays.asList("Memory", 80));
    data.add(Arrays.asList("CPU", 55));
    data.add(Arrays.asList("Network", 68));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
