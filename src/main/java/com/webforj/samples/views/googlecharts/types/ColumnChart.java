package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnChart {

  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.COLUMN);

  public ColumnChart() {

    Map<String, Object> options = new HashMap<>();
    options.put(TITLE, "Annual Sales");
    options.put("hAxis", Map.of(
        TITLE, "Year",
        "minValue", 0,
        "textStyle", Map.of(
            "bold", true,
            "fontSize", 12
        )
    ));
    options.put("vAxis", Map.of(
        TITLE, "Sales (in USD)",
        "minValue", 0,
        "textStyle", Map.of(
            "bold", true,
            "fontSize", 12
        )
    ));
    options.put("colors", List.of("#008000", "#4285f4"));
    options.put("backgroundColor", "transparent");
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Year", "Sales", "Expenses"));
    data.add(Arrays.asList("2014", 1000, 400));
    data.add(Arrays.asList("2015", 1170, 460));
    data.add(Arrays.asList("2016", 660, 1120));
    data.add(Arrays.asList("2017", 1030, 540));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
