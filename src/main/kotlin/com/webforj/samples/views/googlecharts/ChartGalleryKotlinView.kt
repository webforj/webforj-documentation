package com.webforj.samples.views.googlecharts

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.googlecharts.GoogleChart
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.html.elements.anchor
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.views.googlecharts.types.*
import java.util.*

@StyleSheet("ws://css/googlecharts/chartGallery.css")
@Route
@FrameTitle("Chart Gallery")
class ChartGalleryKotlinView: Composite<Div>() {
  private val self = boundComponent
  private val GITHUB_BASE_URL: String =
    "https://github.com/webforj/webforj-documentation/blob/main/src/main/java/com/webforj/samples/views/googlecharts/types/"

  init {
    self.apply {
      classNames + "chart-gallery"
      getChartKeys().forEach { chartLink(it) }
    }
  }

  private fun getChartKeys() = listOf(
      "pie", "column", "bar", "line", "geo", "scatter", "histogram", "combo",
      "area", "stepped_area", "bubble", "org", "treemap", "table", "gauge",
      "candlestick", "sankey", "wordtree", "timeline", "calendar", "gantt"
    )

  private fun Div.chartLink(chartKey: String) {
    chart(chartKey)?.let {
      val formattedTitle = formatTitle(chartKey)
      anchor {
        href = GITHUB_BASE_URL + formattedTitle.replace(" ", "") + ".java"
        target = "_blank"
        div {
          classNames + "chart-div"
          paragraph(formattedTitle)
          add(it)
        }
      }
    }
  }

  private fun chart(chartKey: String): GoogleChart? = when (chartKey.lowercase()) {
      "pie" -> PieChart().chart
      "column" -> ColumnChart().chart
      "bar" -> BarChart().chart
      "line" -> LineChart().chart
      "geo" -> GeoChart().chart
      "scatter" -> ScatterChart().chart
      "histogram" -> HistogramChart().chart
      "combo" -> ComboChart().chart
      "area" -> AreaChart().chart
      "stepped_area" -> SteppedAreaChart().chart
      "bubble" -> BubbleChart().chart
      "org" -> OrgChart().chart
      "treemap" -> TreemapChart().chart
      "table" -> TableChart().chart
      "gauge" -> GaugeChart().chart
      "candlestick" -> CandlestickChart().chart
      "sankey" -> SankeyChart().chart
      "wordtree" -> WordtreeChart().chart
      "timeline" -> TimelineChart().chart
      "calendar" -> CalendarChart().chart
      "gantt" -> GanttChart().chart
      else -> null
    }

  private fun formatTitle(title: String): String {
    if (title.isEmpty()) {
      return title
    }
    val words = title.replace("_", " ")
      .split("\\s+".toRegex())
      .toTypedArray()
    val formattedTitle = StringBuilder()

    for (word in words) {
      if (!word.isEmpty()) {
        formattedTitle.append(word.get(0).uppercaseChar())
          .append(word.substring(1).lowercase(Locale.getDefault()))
          .append(" ")
      }
    }
    return formattedTitle.toString().trim() + " Chart"
  }

}