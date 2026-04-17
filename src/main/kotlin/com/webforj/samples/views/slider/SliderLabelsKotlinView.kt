package com.webforj.samples.views.slider

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.slider.slider
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Slider Tick and Non-Tick Demo")
class SliderLabelsKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "5% auto"
      alignment = FlexAlignment.CENTER

      slider(50, 0, 100) {
        isFilled = true
        isTicksVisible = true
        majorTickSpacing = 10
        minorTickSpacing = 2
        isLabelsVisible = true
        isAllowMajorLabelsOverlap = true
        isTooltipVisible = true
        theme = Theme.SUCCESS
        labels = mapOf(
          0 to "Cold",
          30 to "Cool",
          50 to "Moderate",
          80 to "Warm",
          100 to "Hot"
        )
        isSnapToTicks = true
        width = 500.px
        onValueChange {
          theme = when(it.value) {
            in 1..<30 -> Theme.PRIMARY
            in 30..<50 -> Theme.SUCCESS
            in 50..<80 -> Theme.WARNING
            in 80..100 -> Theme.DANGER
            else -> theme
          }
        }
      }
    }
  }
}
