package com.webforj.samples.views.googlecharts;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route
@StyleSheet("ws://css/googlecharts/chart.css")
@FrameTitle("Google Charts")
public class ChartView extends Composite<Div> {
  private final Div self = getBoundComponent();

  private static final String COLOR = "color";
  private static final String TEXT_STYLE = "textStyle";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.GEO);

  private static final String[] COUNTRIES = {
    "Germany", "United States", "Brazil", "Canada",
    "France", "RU", "Australia", "South Africa",
    "China", "Egypt"
  };

  public ChartView() {
    self.addClassName("chart-frame");
    setupChart();
    self.add(chart);
  }

  private void setupChart() {
    chart.setStyle("width", "100vw");
    chart.setStyle("height", "100vh");

    Map<String, Object> options =
        Map.of(
            "colors", List.of("#006fe6", "#8f64e0", "#ce55ca", "#fa49ab"),
            "backgroundColor", "#f9f9f9",
            "chartArea", Map.of("width", "70%", "height", "80%"),
            "hAxis", Map.of(TEXT_STYLE, Map.of(COLOR, "#333")),
            "vAxis", Map.of("minValue", 0, TEXT_STYLE, Map.of(COLOR, "#333")),
            "legend",
                Map.of(
                    "position",
                    "top",
                    "alignment",
                    "center",
                    TEXT_STYLE,
                    Map.of("fontSize", 16, COLOR, "#333"),
                    "maxLines",
                    3));

    List<Object> data = createChartData();
    chart.setOptions(options);
    chart.setData(data);
  }

  private List<Object> createChartData() {
    List<Object> data = new ArrayList<>();
    List<String> cols = List.of("Country", "Revenue");
    data.add(cols);

    for (String country : COUNTRIES) {
      List<Object> row = List.of(country, Math.random() * 10000);
      data.add(row);
    }

    return data;
  }
}
