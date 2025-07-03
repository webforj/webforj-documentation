package com.webforj.samples.views.googlecharts.types;

import com.webforj.component.googlecharts.GoogleChart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class WordtreeChart {

  private static final String CATSEATMICE = "cats eat mice";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.WORDTREE);

  public WordtreeChart() {

    Map<String, Object> options = new HashMap<>();
    options.put("title", "Word Usage Tree");
    options.put("backgroundColor", "transparent");
    options.put("wordtree", Map.of(
        "format", "implicit",
        "word", "open"
    ));
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Phrases"));
    data.add(Arrays.asList("cats are better than dogs"));
    data.add(Arrays.asList("cats eat kibble"));
    data.add(Arrays.asList("cats are better than hamsters"));
    data.add(Arrays.asList("cats are awesome"));
    data.add(Arrays.asList("cats are people too"));
    data.add(Arrays.asList(CATSEATMICE));
    data.add(Arrays.asList("cats meowing"));
    data.add(Arrays.asList("cats in the cradle"));
    data.add(Arrays.asList(CATSEATMICE));
    data.add(Arrays.asList("cats in the cradle lyrics"));
    data.add(Arrays.asList("cats eat kibble"));
    data.add(Arrays.asList("cats for adoption"));
    data.add(Arrays.asList("cats are family"));
    data.add(Arrays.asList(CATSEATMICE));
    data.add(Arrays.asList("cats are better than kittens"));
    data.add(Arrays.asList("cats are evil"));
    data.add(Arrays.asList("cats are weird"));
    data.add(Arrays.asList(CATSEATMICE));
    chart.setData(data);
  }

  public GoogleChart getChart() {
    return chart;
  }
}
