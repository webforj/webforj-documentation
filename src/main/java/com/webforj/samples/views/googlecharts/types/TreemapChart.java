package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class TreemapChart {
  private static final String AFRICA = "Africa";
  private static final String AMERICA = "America";
  private static final String AUSTRALIA = "Australia";
  private static final String EUROPE = "Europe";
  private static final String GLOBAL = "Global";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.TREEMAP);

  public TreemapChart() {
    Map<String, Object> options = Map.of(
        "title", "Market Segmentation",
        "minColor", "#4285f4",
        "midColor", "#ddd",
        "maxColor", "#ab48bc",
        "headerHeight", 15,
        "fontColor", "black",
        "showScale", true,
        "backgroundColor", "transparent"
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Location", "Parent", 
            "Market trade volume (size)", 
            "Market increase/decrease (color)"),
        List.of(GLOBAL, null, 0, 0),
        List.of(AMERICA, GLOBAL, 0, 0),
        List.of(EUROPE, GLOBAL, 0, 0),
        List.of("Asia", GLOBAL, 0, 0),
        List.of(AUSTRALIA, GLOBAL, 0, 0),
        List.of(AFRICA, GLOBAL, 0, 0),
        List.of("Brazil", AMERICA, 11, 10),
        List.of("USA", AMERICA, 52, 31),
        List.of("Mexico", AMERICA, 24, 12),
        List.of("Canada", AMERICA, 16, -23),
        List.of("France", EUROPE, 42, -11),
        List.of("Germany", EUROPE, 31, -2),
        List.of("Sweden", EUROPE, 22, -13),
        List.of("Italy", EUROPE, 17, 4),
        List.of("UK", EUROPE, 21, -5),
        List.of("China", "Asia", 36, 4),
        List.of("Japan", "Asia", 20, -12),
        List.of("India", "Asia", 40, 63),
        List.of("Laos", "Asia", 4, 34),
        List.of("Australia2", AUSTRALIA, 19, 0),
        List.of("New Zealand", AUSTRALIA, 13, -2),
        List.of("Egypt", AFRICA, 21, 0),
        List.of("South Africa", AFRICA, 30, -23)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
