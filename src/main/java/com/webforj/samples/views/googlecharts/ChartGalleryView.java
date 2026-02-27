package com.webforj.samples.views.googlecharts;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Anchor;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.googlecharts.types.*;
import java.util.List;
import java.util.function.Predicate;

@StyleSheet("ws://css/googlecharts/chartGallery.css")
@Route
@FrameTitle("Chart Gallery")
public class ChartGalleryView extends Composite<Div> {
  private static final String GITHUB_BASE_URL = "https://github.com/webforj/webforj-documentation/blob/main/src/main/java/com/webforj/samples/views/googlecharts/types/";

  private Div self = getBoundComponent();

  public ChartGalleryView() {
    self.addClassName("chart-gallery");

    for (String chartKey : getChartKeys()) {
      GoogleChart chart = createChart(chartKey);

      if (chart != null) {
        Div chartDiv = new Div();
        chartDiv.addClassName("chart-div");

        Paragraph chartName = new Paragraph();
        String formattedTitle = formatTitle(chartKey);
        chartName.setText(formattedTitle);
        chartName.addClassName("chartname");
        chartDiv.add(chartName);

        chartDiv.add(chart);

        Anchor chartLink = new Anchor();
        chartLink.setHref(GITHUB_BASE_URL + formattedTitle.replace(" ", "") + ".java");
        chartLink.setTarget("_blank");
        chartLink.add(chartDiv);

        self.add(chartLink);
      }
    }
  }

  private List<String> getChartKeys() {
    return List.of("pie", "column", "bar", "line", "geo", "scatter", "histogram", "combo",
        "area", "stepped_area", "bubble", "org", "treemap", "table", "gauge",
        "candlestick", "sankey", "wordtree", "timeline", "calendar", "gantt");
  }

  private GoogleChart createChart(String chartKey) {
    return switch (chartKey.toLowerCase()) {
      case "pie" -> new PieChart().getChart();
      case "column" -> new ColumnChart().getChart();
      case "bar" -> new BarChart().getChart();
      case "line" -> new LineChart().getChart();
      case "geo" -> new GeoChart().getChart();
      case "scatter" -> new ScatterChart().getChart();
      case "histogram" -> new HistogramChart().getChart();
      case "combo" -> new ComboChart().getChart();
      case "area" -> new AreaChart().getChart();
      case "stepped_area" -> new SteppedAreaChart().getChart();
      case "bubble" -> new BubbleChart().getChart();
      case "org" -> new OrgChart().getChart();
      case "treemap" -> new TreemapChart().getChart();
      case "table" -> new TableChart().getChart();
      case "gauge" -> new GaugeChart().getChart();
      case "candlestick" -> new CandlestickChart().getChart();
      case "sankey" -> new SankeyChart().getChart();
      case "wordtree" -> new WordtreeChart().getChart();
      case "timeline" -> new TimelineChart().getChart();
      case "calendar" -> new CalendarChart().getChart();
      case "gantt" -> new GanttChart().getChart();
      default -> null;
    };
  }

  private String formatTitle(String title) {
    if (title == null || title.isEmpty()) {
      return title;
    }
    String[] words = title.replace("_", " ").split("\\s+");
    StringBuilder formattedTitle = new StringBuilder();

    for (String word : words) {
      if (!word.isEmpty()) {
        formattedTitle.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
      }
    }
    return formattedTitle.toString().trim() + " Chart";
  }
}
