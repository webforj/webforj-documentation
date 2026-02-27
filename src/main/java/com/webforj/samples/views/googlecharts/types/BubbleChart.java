package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class BubbleChart {
  private static final String TITLE = "title";
  private static final String TEXT_STYLE = "textStyle";
  private static final String FONT_SIZE = "fontSize";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.BUBBLE);

  public BubbleChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Investment vs. Profit",
        "hAxis", Map.of(
            TITLE, "Potential Investment",
            TEXT_STYLE, Map.of(
                "bold", true,
                FONT_SIZE, 12,
                "color", "#4d4d4d"
            )
        ),
        "vAxis", Map.of(
            TITLE, "Potential Profit",
            TEXT_STYLE, Map.of(
                "bold", true,
                FONT_SIZE, 12,
                "color", "#4d4d4d"
            )
        ),
        "bubble", Map.of(TEXT_STYLE, Map.of(FONT_SIZE, 11)),
        "colorAxis", Map.of("colors", List.of("#d4c5f9", "#495057")),
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("ID", "X", "Y", "Temperature"),
        List.of("1", 80, 167, 120),
        List.of("2", 79, 136, 130),
        List.of("3", 78, 184, 50),
        List.of("4", 72, 278, 230)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}


