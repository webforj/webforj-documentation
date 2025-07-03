package com.webforj.samples.views.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.webforj.Page;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Analytics Card")
@StyleSheet("ws://composite/analyticscomposite.css")
public class AnalyticsCardCompositeView extends Composite<Div> {

  private final Paragraph title = new Paragraph("Monthly Sales");
  private final Span value = new Span("$45,000");
  private final ProgressBar progress = new ProgressBar("75% of monthly goal reached");

  public AnalyticsCardCompositeView() {
    getBoundComponent().addClassName("analytics-card");

    FlexLayout content = new FlexLayout()
      .setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setAlignment(FlexAlignment.START);

    title.addClassName("analytics-title");
    value.addClassName("analytics-value");
    
    FlexLayout changeRow = new FlexLayout();
    changeRow.setSpacing("var(--dwc-space-xs)")
             .setAlignment(FlexAlignment.CENTER);

    Span changeText = new Span("12% from last month");
    changeText.setStyle("color", "var(--dwc-color-success-600)")
              .setStyle("font-size", "0.9rem");

    Icon upArrow = TablerIcon.create("arrow-up");

    changeRow.add(upArrow, changeText);

    Span progressLabel = new Span("75% of target achieved");
    progressLabel.setStyle("font-size", "0.85rem")
                 .setStyle("color", "var(--dwc-color-neutral-600)");

    progress.setValue(75);
    progress.setStyle("margin-top", "var(--dwc-space-xs)");
    
    GoogleChart chart = new GoogleChart(GoogleChart.Type.LINE);
    chart.setSize("100%", "250px").setStyle("width", "100%");

    List<Object> data = new ArrayList<>();
    data.add(List.of("Month", "Sales"));
    data.add(List.of("Jan", 25000));
    data.add(List.of("Feb", 32000));
    data.add(List.of("Mar", 27000));
    data.add(List.of("Apr", 35000));
    data.add(List.of("May", 45000));

    chart.setData(data);
    
    Map<String, Object> options = new HashMap<>();
    options.put("backgroundColor", "transparent");

    Map<String, Object> textStyle = Map.of("color", "#666666");
    options.put("titleTextStyle", textStyle);
    options.put("hAxis", Map.of("textStyle", textStyle));
    options.put("vAxis", Map.of("textStyle", textStyle));
    options.put("legendTextStyle", textStyle);
    
    chart.setOptions(options);

    content.add(title, value, changeRow, progress, chart);
    getBoundComponent().add(content);
  }
}