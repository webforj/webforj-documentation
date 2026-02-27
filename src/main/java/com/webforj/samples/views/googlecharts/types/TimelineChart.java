package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class TimelineChart {
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.TIMELINE);

  public TimelineChart() {
    Map<String, Object> options = Map.of(
        "title", "Presidential Terms",
        "timeline", Map.of(
            "showRowLabels", false
        ),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Term", "President", 
            Map.of("type", "date", "label", "Start"), 
            Map.of("type", "date", "label", "End")
        ),
        List.of("1", "George Washington", "Date(1789, 3, 30)", "Date(1797, 2, 4)"),
        List.of("2", "John Adams", "Date(1797, 2, 4)", "Date(1801, 2, 4)"),
        List.of("3", "Thomas Jefferson", "Date(1801, 2, 4)", "Date(1809, 2, 4)"),
        List.of("4", "James Madison", "Date(1809, 2, 4)", "Date(1817, 2, 4)"),
        List.of("5", "James Monroe", "Date(1817, 2, 4)", "Date(1825, 2, 4)")
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
