package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GanttChart {

  private static final String TITLE = "title";
  private static final String LABEL = "label";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.GANTT);

  public GanttChart() {

    Map<String, Object> options = new HashMap<>();
    options.put(TITLE, "Project Schedule");
    options.put("gantt", Map.of(
        "labelStyle", Map.of(
            "fontSize", 14,
            "color", "#757575"
        ),
        "backgroundColor", "transparent"
    ));
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Task ID", "Task Name",
        Map.of("type", "date", LABEL, "Start Date"),
        Map.of("type", "date", LABEL, "End Date"),
        Map.of("type", "number", LABEL, "Duration"),
        Map.of("type", "number", LABEL, "Percent Complete"),
        Map.of("type", "string", LABEL, "Dependencies")
    ));
    data.add(Arrays.asList(
        "Research", "Find Topics", "Date(2022, 0, 1)", "Date(2022, 0, 3)", null, 100, null
    ));
    data.add(Arrays.asList(
        "Write", "Draft Outline", "Date(2022, 0, 4)", "Date(2022, 0, 6)", null, 25, "Research"
    ));
    data.add(Arrays.asList(
        "Review", "Revise Draft", "Date(2022, 0, 7)", "Date(2022, 0, 9)", null, 0, "Write"
    ));
    data.add(Arrays.asList(
        "Finalize", "Complete Article", "Date(2022, 0, 10)", "Date(2022, 0, 12)", null, 0, "Review"
    ));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
