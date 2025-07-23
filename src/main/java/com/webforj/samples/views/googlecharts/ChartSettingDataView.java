package com.webforj.samples.views.googlecharts;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InlineStyleSheet(/*css*/ """
    .window {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
""")
@Route
@FrameTitle("Chart Setting Data")
public class ChartSettingDataView extends Composite<Div> {

  GoogleChart chart = new GoogleChart(GoogleChart.Type.PIE);

  public ChartSettingDataView() {

    getBoundComponent().addClassName("window");

    chart.setStyle("width", "100vw");
    chart.setStyle("height", "100vh");


    Map<String, Object> options = new HashMap<>();
    options.put("title", "Sales Distribution by Region");
    options.put("is3D", "true");
    options.put(
        "colors",
        Arrays.asList("#BBDEFB", "#64B5F6", "#1E88E5", "#0D47A1", "#1565C0", "#82B1FF")
    );

    List<Object> data = new ArrayList<>();
    data.add(Arrays.asList("Region", "Sales"));
    data.add(Arrays.asList("North America", 500));
    data.add(Arrays.asList("Europe", 300));
    data.add(Arrays.asList("Asia", 200));
    data.add(Arrays.asList("Latin America", 100));
    data.add(Arrays.asList("Middle East", 80));
    data.add(Arrays.asList("Africa", 60));
    chart.setData(data);

    chart.setOptions(options);
    getBoundComponent().add(chart);
  }
}
