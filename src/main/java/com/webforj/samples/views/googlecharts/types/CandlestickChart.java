package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class CandlestickChart {
  private static final String TYPE_NUMBER = "number";
  private static final String LABEL = "label";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.CANDLESTICK);

  public CandlestickChart() {
    Map<String, Object> options = Map.of(
        "title", "Stock Market Trends",
        "legend", "none",
        "bar", Map.of("groupWidth", "20%"),
        "candlestick", Map.of(
            "fallingColor", Map.of("fill", "#a52714"),
            "risingColor", Map.of("fill", "#0f9d58")
        ),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of(
            Map.of("type", "string", LABEL, "Day"),
            Map.of("type", TYPE_NUMBER, LABEL, "Low"),
            Map.of("type", TYPE_NUMBER, LABEL, "Opening"),
            Map.of("type", TYPE_NUMBER, LABEL, "Closing"),
            Map.of("type", TYPE_NUMBER, LABEL, "High")
        ),
        List.of("Mon", 20, 28, 38, 45),
        List.of("Tue", 31, 38, 55, 66),
        List.of("Wed", 50, 55, 60, 70),
        List.of("Thu", 35, 40, 50, 55),
        List.of("Fri", 20, 22, 30, 50)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}


