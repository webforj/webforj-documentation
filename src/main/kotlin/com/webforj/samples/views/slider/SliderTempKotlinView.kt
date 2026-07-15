package com.webforj.samples.views.slider

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.slider.Slider
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.slider.slider
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.rem
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Temperature Selector Demo")
class SliderTempKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private lateinit var temperatureSlider: Slider

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-l)"

      flexLayout {
        horizontal()
        justifyContent = FlexJustifyContent.BETWEEN
        alignment = FlexAlignment.CENTER
        spacing = "var(--dwc-space-m)"

        iconButton("snowflake", "tabler") {
          theme = Theme.PRIMARY
          styles["font-size"] = 1.5.rem
          onClick { temperatureSlider.value = 60 }
        }

        temperatureSlider = slider(72, 60, 90) {
          isTicksVisible = true
          majorTickSpacing = 10
          minorTickSpacing = 5
          labels = mapOf(
            60 to "60\u00B0F",
            70 to "70\u00B0F",
            80 to "80\u00B0F",
            90 to "90\u00B0F"
          )
          isLabelsVisible = true
          isTooltipVisibleOnSlideOnly = true
          width = 300.px
        }

        iconButton("sun", "tabler") {
          theme = Theme.DANGER
          styles["font-size"] = 1.5.rem
          onClick { temperatureSlider.value = 90 }
        }
      }
    }
  }
}
