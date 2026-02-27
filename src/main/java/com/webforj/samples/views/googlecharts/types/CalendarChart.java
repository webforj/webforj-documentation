package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class CalendarChart {
  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.CALENDAR);

  public CalendarChart() {
    Map<String, Object> options = Map.of(
        "title", "Attendance Calendar",
        "calendar", Map.of(
            "cellColor", Map.of(
                "stroke", "#fff",
                "strokeOpacity", 0.5,
                "strokeWidth", 1,
                "fill", "#76a7fa"
            ),
            "focusedCellColor", Map.of(
                "stroke", "#fff",
                "strokeOpacity", 0.5,
                "strokeWidth", 1,
                "fill", "#4285f4"
            )
        ),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of(
            Map.of("type", "date", "label", "Date"),
            Map.of("type", "number", "label", "Attendance")
        ),
        List.of("Date(2022, 0, 1)", 300),
        List.of("Date(2022, 0, 4)", 600),
        List.of("Date(2022, 0, 5)", 200),
        List.of("Date(2022, 0, 7)", 300),
        List.of("Date(2022, 0, 8)", 400),
        List.of("Date(2022, 0, 11)", 200),
        List.of("Date(2022, 0, 14)", 400),
        List.of("Date(2022, 0, 18)", 300),
        List.of("Date(2022, 0, 19)", 400),
        List.of("Date(2022, 0, 21)", 600),
        List.of("Date(2022, 0, 22)", 200),
        List.of("Date(2022, 0, 26)", 600),
        List.of("Date(2022, 0, 29)", 300),
        List.of("Date(2022, 0, 31)", 500)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}

