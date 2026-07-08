package com.webforj.samples.views.composite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;
import java.util.Map;

@Route
@FrameTitle("Analytics Card")
@BundleEntry("composite/analyticscomposite.css")
public class AnalyticsCardCompositeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public AnalyticsCardCompositeView() {
    Div card = new Div();
    card.addClassName("analytics-card").add(buildContent());

    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.setJustifyContent(FlexJustifyContent.CENTER);
    self.addClassName("analytics-stage");
    self.add(card);
  }

  private FlexLayout buildContent() {
    ProgressBar progress =
        new ProgressBar("75% of monthly goal reached")
            .setValue(75)
            .setStyle("margin-top", "var(--dwc-space-xs)");

    return FlexLayout.create(buildHeader(), buildChangeRow(), progress, buildChart())
        .vertical()
        .align()
        .start()
        .build()
        .setSpacing("var(--dwc-space-m)");
  }

  private FlexLayout buildHeader() {
    Paragraph title = new Paragraph("Monthly Sales").addClassName("analytics-title");

    Span value = new Span("$45,000").addClassName("analytics-value");

    return FlexLayout.create(title, value).vertical().build().setSpacing("var(--dwc-space-xs)");
  }

  private FlexLayout buildChangeRow() {
    Span changeText = new Span("12% from last month").addClassName("analytics-description");

    Icon upArrow = TablerIcon.create("arrow-up").setTheme(Theme.SUCCESS);

    return FlexLayout.create(upArrow, changeText)
        .align()
        .center()
        .build()
        .setSpacing("var(--dwc-space-xs)");
  }

  private GoogleChart buildChart() {
    List<Object> data =
        List.of(
            List.of("Month", "Sales"),
            List.of("Jan", 25000),
            List.of("Feb", 32000),
            List.of("Mar", 27000),
            List.of("Apr", 35000),
            List.of("May", 45000));

    Map<String, Object> textStyle = Map.of("color", "#666666");
    Map<String, Object> options =
        Map.of(
            "backgroundColor",
            "transparent",
            "titleTextStyle",
            textStyle,
            "hAxis",
            Map.of("textStyle", textStyle),
            "vAxis",
            Map.of("textStyle", textStyle),
            "legendTextStyle",
            textStyle);

    return new GoogleChart(GoogleChart.Type.LINE)
        .setSize("100%", "250px")
        .setData(data)
        .setOptions(options);
  }
}
