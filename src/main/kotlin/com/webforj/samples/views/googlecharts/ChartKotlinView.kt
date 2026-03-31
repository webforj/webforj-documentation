package com.webforj.samples.views.googlecharts

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.googlecharts.GoogleChart
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.googlecharts.googleChart
import com.webforj.kotlin.extension.*
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@StyleSheet("ws://css/googlecharts/chart.css")
@FrameTitle("Google Charts")
class ChartKotlinView: Composite<Div>() {
  private val self = boundComponent
  private val countries = arrayOf(
    "Germany", "United States", "Brazil", "Canada",
    "France", "RU", "Australia", "South Africa",
    "China", "Egypt"
  )

  init {
    self.apply {
      classNames + "chart-frame"
      googleChart(GoogleChart.Type.GEO) {
        styles["width"] = 100.vw
        styles["height"] = 100.vh
        val color = "color"
        val textStyle = "textStyle"
        options = mapOf(
          "colors" to listOf("#006fe6", "#8f64e0", "#ce55ca", "#fa49ab"),
          "backgroundColor" to "#f9f9f9",
          "chartArea" to mapOf("width" to "70%", "height" to "80%"),
          "hAxis" to mapOf(textStyle to mapOf(color to "#333")),
          "vAxis" to mapOf("minValue" to 0, textStyle to mapOf(color to "#333")),
          "legend" to mapOf(
            "position" to "top",
            "alignment" to "center",
            textStyle to mapOf("fontSize" to 16, color to "#333"),
            "maxLines" to 3
          )
        )
        data = arrayListOf<Any>().apply {
            add(listOf("Country", "Revenue"))
            countries.forEach {
              add(listOf(it, Math.random() * 10000))
            }
          }
      }
    }
  }
}