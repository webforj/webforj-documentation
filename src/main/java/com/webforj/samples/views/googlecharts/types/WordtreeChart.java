package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.List;
import java.util.Map;

public class WordtreeChart {
  private static final String CATSEATMICE = "cats eat mice";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.WORDTREE);

  public WordtreeChart() {
    Map<String, Object> options = Map.of(
        "title", "Word Usage Tree",
        "backgroundColor", "transparent",
        "wordtree", Map.of(
            "format", "implicit",
            "word", "open"
        )
    );
    chart.setOptions(options);

    List<Object> data = List.of(
        List.of("Phrases"),
        List.of("cats are better than dogs"),
        List.of("cats eat kibble"),
        List.of("cats are better than hamsters"),
        List.of("cats are awesome"),
        List.of("cats are people too"),
        List.of(CATSEATMICE),
        List.of("cats meowing"),
        List.of("cats in the cradle"),
        List.of(CATSEATMICE),
        List.of("cats in the cradle lyrics"),
        List.of("cats eat kibble"),
        List.of("cats for adoption"),
        List.of("cats are family"),
        List.of(CATSEATMICE),
        List.of("cats are better than kittens"),
        List.of("cats are evil"),
        List.of("cats are weird"),
        List.of(CATSEATMICE)
    );
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
