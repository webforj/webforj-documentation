package com.webforj.samples.views.googlecharts;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;

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
  private Div self = getBoundComponent();
  private GoogleChart chart = new GoogleChart(GoogleChart.Type.PIE);

  public ChartSettingDataView() {
    self.addClassName("window");

    chart.setStyle("width", "100vw")
            .setStyle("height", "100vh");

    Map<String, Object> options = Map.of(
        "title", "Sales Distribution by Region",
        "is3D", "true",
        "colors", List.of("#BBDEFB", "#64B5F6", "#1E88E5", "#0D47A1", "#1565C0", "#82B1FF")
    );

    List<Object> data = List.of(
        List.of("Region", "Sales"),
        List.of("North America", 500),
        List.of("Europe", 300),
        List.of("Asia", 200),
        List.of("Latin America", 100),
        List.of("Middle East", 80),
        List.of("Africa", 60)
    );
    chart.setData(data);

    chart.setOptions(options);
    self.add(chart);
  }
}
