package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class OrgChart {

  private static final String PRESIDENT = "President";
  private static final String SALESVP = "VP of Sales";
  private static final String MARKETINGVP = "VP of Marketing";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.ORG);

  public OrgChart() {
    Map<String, Object> options = new HashMap<>();
    options.put("title", "Company Organization Structure");
    options.put("size", "medium");
    options.put("backgroundColor", "transparent");
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Name", "Manager", "Tooltip"));
    data.add(Arrays.asList(PRESIDENT, "", "The President"));
    data.add(Arrays.asList(SALESVP, PRESIDENT, SALESVP));
    data.add(Arrays.asList(MARKETINGVP, PRESIDENT, MARKETINGVP));
    data.add(Arrays.asList("Sales Manager 1", SALESVP, "Sales Manager 1"));
    data.add(Arrays.asList("Sales Manager 2", SALESVP, "Sales Manager 2"));
    data.add(Arrays.asList("Marketing Manager 1", MARKETINGVP, "Marketing Manager 1"));
    data.add(Arrays.asList("Marketing Manager 2", MARKETINGVP, "Marketing Manager 2"));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
