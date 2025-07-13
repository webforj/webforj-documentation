package com.webforj.samples.views.googlecharts;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InlineStyleSheet("context://css/googlecharts/chart.css")
@Route(RouteConfig.CHART_VIEW)
@FrameTitle("Google Charts")
public class ChartView extends Composite<Div> {

  private static final String COLOR = "color";
  private static final String TEXT_STYLE = "textStyle";

  GoogleChart chart = new GoogleChart(GoogleChart.Type.GEO);

  public ChartView() {
    getBoundComponent().addClassName("chart-frame");

    chart.setStyle("width", "100vw");
    chart.setStyle("height", "100vh");

    Map<String, Object> options = new HashMap<>();
    options.put("colors", List.of("#006fe6", "#8f64e0", "#ce55ca", "#fa49ab"));
    options.put("backgroundColor", "#f9f9f9");
    options.put("chartArea", Map.of("width", "70%", "height", "80%"));
    options.put("hAxis", Map.of(TEXT_STYLE, Map.of(COLOR, "#333")));
    options.put("vAxis", Map.of("minValue", 0, TEXT_STYLE, Map.of(COLOR, "#333")));
    options.put("legend", Map.of(
        "position", "top",
        "alignment", "center",
        TEXT_STYLE, Map.of(
            "fontSize", 16,
            COLOR, "#333"),
        "maxLines", 3));
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    List<String> cols = new ArrayList<>();
    cols.add("Country");
    cols.add("Revenue");
    data.add(cols);

    String[] countries = new String[] {
        "Germany", "United States", "Brazil", "Canada",
        "France", "RU", "Australia", "South Africa",
        "China", "Egypt"
    };

    for (String country : countries) {
      List<Object> row = new ArrayList<>();
      row.add(country);
      row.add(Math.random() * 10000);
      data.add(row);
    }

    chart.setData(data);
    getBoundComponent().add(chart);
  }
}
