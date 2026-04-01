package com.webforj.samples.views.fields.colorfield

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.event.ModifyEvent
import com.webforj.component.field.ColorField
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.colorField
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.awt.Color

@StyleSheet("ws://css/fields/colorfield/colorFieldDemo.css")
@Route
@FrameTitle("Color Field Demo")
class ColorFieldKotlinView: Composite<FlexLayout>() {
  private val colorField: ColorField
  private val colors = Array<Div>(4) {
    Div().addClassName("colorDiv")
  }

  init {
    boundComponent.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-m)"
      colorField = colorField("Choose a color:") {
        width = "200px"
        value = Color.RED
        onModify(::tetradicColor)
      }
      paragraph("Tetradic complementary colors:")
      flexLayout {
        horizontal()
        justifyContent = FlexJustifyContent.CENTER
        alignment = FlexAlignment.CENTER
        spacing = "20px"
        add(*colors)
      }
    }
    tetradicColor(null)
  }

  private fun tetradicColor(e: ModifyEvent?) {
    val selected = colorField.value
    colors[0].setBackgroundColor(selected)
    val baseHue = getHue(selected)
    for (i in 1..3) {
      val hue = (baseHue + i * 60) % 360;
      colors[i].setBackgroundColor(Color.getHSBColor(hue / 360f, getSaturation(selected), getBrightness(selected)))
    }
  }

  private fun Div.setBackgroundColor(color: Color) {
    styles["background-color"] = "rgb(${color.red},${color.green},${color.blue})"
  }

  private fun getHue(color: Color): Int {
    val hsbValues = color.toHSB()
    return (hsbValues[0] * 360).toInt()
  }

  private fun getSaturation(color: Color) = color.toHSB()[1]

  private fun getBrightness(color: Color) = color.toHSB()[2]

  private fun Color.toHSB() = Color.RGBtoHSB(red, green, blue, null)
}