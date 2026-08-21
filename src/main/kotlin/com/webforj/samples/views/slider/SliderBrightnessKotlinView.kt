package com.webforj.samples.views.slider

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexContentAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.slider.Slider
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.slider.slider
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Brightness Adjustment Demo")
class SliderBrightnessKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private lateinit var brightnessSlider: Slider

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      alignContent = FlexContentAlignment.CENTER
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-l)"

      flexLayout {
        horizontal()
        justifyContent = FlexJustifyContent.CENTER
        alignment = FlexAlignment.CENTER
        spacing = "var(--dwc-space-m)"

        iconButton("sun-low", "tabler") {
          onClick { brightnessSlider.value = 0 }
        }

        brightnessSlider = slider(50, 0, 100) {
          majorTickSpacing = 25
          minorTickSpacing = 5
          isTicksVisible = true
          labels = mapOf(
            0 to "Dim",
            50 to "Normal",
            100 to "Bright"
          )
          isLabelsVisible = true
          isTooltipVisibleOnSlideOnly = true
          width = 300.px
        }

        iconButton("sun-high", "tabler") {
          onClick { brightnessSlider.value = 100 }
        }
      }
    }
  }
}
