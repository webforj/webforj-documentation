package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class OrgChart {
  private static final String PRESIDENT = "President";
  private static final String SALESVP = "VP of Sales";
  private static final String MARKETINGVP = "VP of Marketing";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.ORG);

  public OrgChart() {
    Map<String, Object> options = Map.of(
        "title", "Company Organization Structure",
        "size", "medium",
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Name", "Manager", "Tooltip"),
        List.of(PRESIDENT, "", "The President"),
        List.of(SALESVP, PRESIDENT, SALESVP),
        List.of(MARKETINGVP, PRESIDENT, MARKETINGVP),
        List.of("Sales Manager 1", SALESVP, "Sales Manager 1"),
        List.of("Sales Manager 2", SALESVP, "Sales Manager 2"),
        List.of("Marketing Manager 1", MARKETINGVP, "Marketing Manager 1"),
        List.of("Marketing Manager 2", MARKETINGVP, "Marketing Manager 2")
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
