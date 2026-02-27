package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class TableChart {
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.TABLE);

  public TableChart() {
    Map<String, Object> options = Map.of(
        "title", "Sales Data",
        "showRowNumber", true,
        "width", "100%",
        "height", "400px",
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Name", "Salary", "Full Time", "Start Date"),
        List.of("John Smith", 30000, true, "2019-01-10"),
        List.of("Jane Doe", 50000, true, "2020-05-29"),
        List.of("Emily Johnson", 45000, false, "2018-06-15"),
        List.of("Michael Brown", 55000, true, "2021-03-30")
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
