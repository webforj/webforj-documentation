package com.webforj.samples.views.googlecharts

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.NumberField
import com.webforj.component.googlecharts.GoogleChart
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.googlecharts.googleChart
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.toast.toast
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/googlecharts/chartRedraw.css")
@Route
@FrameTitle("Chart Redraw")
class ChartRedrawKotlinView: Composite<Div>() {
  private val self = boundComponent
  private lateinit var chart: GoogleChart
  private val color: String = "color"
  private val textStyle: String = "textStyle"
  private val title: String = "title"
  private val maxAllowed: Long = 1000L
  private val categories = listOf("Instagram", "Twitter", "Facebook", "LinkedIn")
  private val valueFields = hashMapOf<String, NumberField>()

  init {
    self.apply {
      classNames + "window"

      div {
        classNames + "chart-container"
        chart = googleChart(GoogleChart.Type.COLUMN) {
          options = mapOf(
            title to "Social Media Following",
            "colors" to listOf("#006fe6"),
            "backgroundColor" to "transparent",
            "chartArea" to mapOf("width" to "80%", "height" to "70%"),
            "hAxis" to mapOf(textStyle to mapOf(color to "#333")),
            "vAxis" to mapOf("minValue" to 0, textStyle to mapOf(color to "#333")),
            "legend" to mapOf("position" to "bottom")
          )
          data = arrayListOf<Any>().apply {
            add(listOf("Category", "Number of Followers in Thousands"))
            categories.forEach { add(listOf(it, 100)) }
          }
        }
      }
      flexLayout {
        classNames + "input-container"
        justifyContent = FlexJustifyContent.CENTER
        wrap = FlexWrap.WRAP
        spacing = 10.px

        categories.forEach {
          valueFields[it] = numberField("Value for $it") {
            placeholder = ""
            step = 1.0
            min = 1.0
            max = maxAllowed.toDouble()
            text = "100"
            classNames + "number-field"
          }
        }
      }
      div {
        classNames + "redraw-button-container"

        button("Redraw Chart") {
          theme = ButtonTheme.PRIMARY
          classNames + "redraw-button"
          onClick {
            val data = arrayListOf<Any>()
            data.add(listOf("Category", "Number of Followers in Thousands"))
            var allValuesValid = true

            for (category in categories) {
              val valueField = valueFields[category]

              try {
                val value = valueField!!.text.toLong()
                if (value !in 1..maxAllowed) {
                  allValuesValid = false
                  break
                }
                data.add(listOf(category, value))
              } catch (_: NumberFormatException) {
                allValuesValid = false
                break
              }
            }

            if (!allValuesValid) {
              Toast.show("Enter a valid number between 1 and $maxAllowed", 3000, Theme.DANGER)
            } else {
              chart.data = data
              chart.redraw()
            }
          }
        }
      }
    }
  }
}
