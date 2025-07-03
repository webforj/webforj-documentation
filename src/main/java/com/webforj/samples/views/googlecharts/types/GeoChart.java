package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class GeoChart {

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.GEO);

  public GeoChart() {

    Map<String, Object> options = new HashMap<>();
    options.put("title", "Geo Chart");
    options.put("colorAxis", Map.of("colors", List.of("#4285f4", "#ab48bc")));
    options.put("backgroundColor", "transparent");
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Country", "Popularity"));
    data.add(Arrays.asList("Germany", 200));
    data.add(Arrays.asList("United States", 300));
    data.add(Arrays.asList("Brazil", 400));
    data.add(Arrays.asList("Canada", 500));
    data.add(Arrays.asList("France", 600));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
