package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TreemapChart {

  private static final String AFRICA = "Africa";
  private static final String AMERICA = "America";
  private static final String AUSTRALIA = "Australia";
  private static final String EUROPE = "Europe";
  private static final String GLOBAL = "Global";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.TREEMAP);

  public TreemapChart() {
    Map<String, Object> options = new HashMap<>();
    options.put("title", "Market Segmentation");
    options.put("minColor", "#4285f4");
    options.put("midColor", "#ddd");
    options.put("maxColor", "#ab48bc");
    options.put("headerHeight", 15);
    options.put("fontColor", "black");
    options.put("showScale", true);
    options.put("backgroundColor", "transparent");
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Location", "Parent",
        "Market trade volume (size)",
        "Market increase/decrease (color)"));
    data.add(Arrays.asList(GLOBAL, null, 0, 0)); // Allow null
    data.add(Arrays.asList(AMERICA, GLOBAL, 0, 0));
    data.add(Arrays.asList(EUROPE, GLOBAL, 0, 0));
    data.add(Arrays.asList("Asia", GLOBAL, 0, 0));
    data.add(Arrays.asList(AUSTRALIA, GLOBAL, 0, 0));
    data.add(Arrays.asList(AFRICA, GLOBAL, 0, 0));
    data.add(Arrays.asList("Brazil", AMERICA, 11, 10));
    data.add(Arrays.asList("USA", AMERICA, 52, 31));
    data.add(Arrays.asList("Mexico", AMERICA, 24, 12));
    data.add(Arrays.asList("Canada", AMERICA, 16, -23));
    data.add(Arrays.asList("France", EUROPE, 42, -11));
    data.add(Arrays.asList("Germany", EUROPE, 31, -2));
    data.add(Arrays.asList("Sweden", EUROPE, 22, -13));
    data.add(Arrays.asList("Italy", EUROPE, 17, 4));
    data.add(Arrays.asList("UK", EUROPE, 21, -5));
    data.add(Arrays.asList("China", "Asia", 36, 4));
    data.add(Arrays.asList("Japan", "Asia", 20, -12));
    data.add(Arrays.asList("India", "Asia", 40, 63));
    data.add(Arrays.asList("Laos", "Asia", 4, 34));
    data.add(Arrays.asList("Australia2", AUSTRALIA, 19, 0));
    data.add(Arrays.asList("New Zealand", AUSTRALIA, 13, -2));
    data.add(Arrays.asList("Egypt", AFRICA, 21, 0));
    data.add(Arrays.asList("South Africa", AFRICA, 30, -23));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
