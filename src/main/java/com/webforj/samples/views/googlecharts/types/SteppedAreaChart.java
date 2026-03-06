package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class SteppedAreaChart {
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.STEPPED_AREA);

  public SteppedAreaChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Energy Consumption",
        "backgroundColor", "transparent",
        "hAxis", Map.of(
            TITLE, "Year",
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        ),
        "vAxis", Map.of(
            TITLE, "Value",
            "textStyle", Map.of(
                "bold", true,
                "fontSize", 12,
                "color", "#4d4d4d"
            )
        )
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Director (Year)", "Rotten Tomatoes", "IMDB"),
        List.of("Alfred Hitchcock (1935)", 8.4, 7.9),
        List.of("Ridley Scott (1979)", 8.5, 8.4),
        List.of("Peter Jackson (2001)", 9.0, 8.8),
        List.of("Christopher Nolan (2008)", 9.0, 9.0),
        List.of("Martin Scorsese (1980)", 8.7, 8.8)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
