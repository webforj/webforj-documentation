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
import com.webforj.kotlin.dsl.component.slider.slider
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Slider Orientation")
class SliderOrientationKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-l)"

      flexLayout {
        direction = FlexDirection.COLUMN
        justifyContent = FlexJustifyContent.CENTER
        alignment = FlexAlignment.CENTER
        spacing = "var(--dwc-space-m)"

        val maxVolumeButton = iconButton("volume-2", "tabler")
        val volumeSlider = slider(50, 0, 100) {
          isFilled = true
          theme = Theme.GRAY
          orientation = Slider.Orientation.VERTICAL
          isTicksVisible = true
          majorTickSpacing = 20
          minorTickSpacing = 10
          labels = mapOf(
            0 to "Mute",
            20 to 20.percent,
            40 to 40.percent,
            60 to 60.percent,
            80 to 80.percent,
            100 to  "Max"
          )
          isLabelsVisible = true
          isTooltipVisible = true
          isTooltipVisibleOnSlideOnly = true
        }
        maxVolumeButton.onClick { volumeSlider.value = 100 }
        iconButton("volume-off", "tabler") {
          theme = Theme.DANGER
          onClick { volumeSlider.value = 0 }
        }
      }
    }
  }
}
