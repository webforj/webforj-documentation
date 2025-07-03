package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaChart {

  private static final String TITLE = "title";
  private static final String TEXT_STYLE = "textStyle";
  private static final String COLOR = "color";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.AREA);

  public AreaChart() {

    Map<String, Object> options = new HashMap<>();
    options.put(TITLE, "Market Trends");
    options.put("colors", List.of("#34a853", "#4285f4", "#fbbc05", "#ea4335"));
    options.put("backgroundColor", "transparent");
    options.put("hAxis", Map.of(
        TITLE, "Year",
        TEXT_STYLE, Map.of(
            "bold", true,
            "fontSize", 12,
            COLOR, "#4d4d4d"
        )
    ));
    options.put("vAxis", Map.of(
        TITLE, "Value",
        TEXT_STYLE, Map.of(
            "bold", true,
            "fontSize", 12,
            COLOR, "#4d4d4d"
        )
    ));
    options.put("isStacked", true);
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Year", "Sales", "Expenses"));
    data.add(Arrays.asList("2013", 1000, 400));
    data.add(Arrays.asList("2014", 1170, 460));
    data.add(Arrays.asList("2015", 660, 600));
    data.add(Arrays.asList("2016", 1030, 540));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
