package com.webforj.samples.views.googlecharts

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.googlecharts.GoogleChart
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.googlecharts.googleChart
import com.webforj.kotlin.extension.*
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@InlineStyleSheet(
  """
    .window {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    
    """
)
@Route
@FrameTitle("Chart Setting Data")
class ChartSettingDataKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      classNames + "window"
      googleChart(GoogleChart.Type.PIE) {
        styles["width"] = 100.vw
        styles["height"] = 100.vh
        options = mapOf(
          "title" to "Sales Distribution by Region",
          "is3D" to "true",
          "colors" to listOf("#BBDEFB", "#64B5F6", "#1E88E5", "#0D47A1", "#1565C0", "#82B1FF")
        )
        data = listOf(
          listOf("Region", "Sales"),
          listOf("North America", 500),
          listOf("Europe", 300),
          listOf("Asia", 200),
          listOf("Latin America", 100),
          listOf("Middle East", 80),
          listOf("Africa", 60)
        )
      }
    }
  }
}