package com.webforj.samples.views.slider

import com.webforj.component.Composite
import com.webforj.component.field.NumberField
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.slider.Slider
import com.webforj.data.event.ValueChangeEvent
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.dsl.component.slider.slider
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import kotlin.math.min

@Route
@FrameTitle("Slider Major and Minor Tick Spacing Demo")
class SliderTickSpacingKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private val slider: Slider
  private lateinit var majorTickInput: NumberField
  private lateinit var minorTickInput: NumberField

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      maxWidth = 400.px
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-m) auto"
      slider = slider(0, 0, 100) {
        isFilled = true
        isTicksVisible = true
        majorTickSpacing = 20
        minorTickSpacing = 10
        isLabelsVisible = true
        isTooltipVisibleOnSlideOnly = true
        styles["padding"] = "var(--dwc-space-m) 0"
        flexLayout {
          vertical()
          majorTickInput = numberField("Major Tick", 20.0) {
            min = 1.0
            max = 100.0
            invalidMessage = "Must be between 1 and 100"
            placeholder = "Enter major tick spacing (e.g., 10)"
            onValueChange(::updateTickSpacing)
          }
          minorTickInput = numberField("Minor Tick", 10.0) {
            min = 1.0
            max = 100.0
            invalidMessage = "Must be between 1 and 100"
            placeholder = "Enter minor tick spacing (e.g., 2)"
            onValueChange(::updateTickSpacing)
          }
          flexLayout {
            horizontal()
            switch("Snap to Ticks", false) {
              onToggle { slider.isSnapToTicks = it.isToggled }
            }
            switch("Show Ticks", true) {
              onToggle { slider.isTicksVisible = it.isToggled }
            }
          }
        }
      }
    }
  }

  private fun updateTickSpacing(ev: ValueChangeEvent<Double>) {
    ev.value?.let {
      if (ev.source == majorTickInput && !majorTickInput.isInvalid) {
        slider.majorTickSpacing = it.toInt()
      } else if (ev.source == minorTickInput && !minorTickInput.isInvalid) {
        slider.minorTickSpacing = it.toInt()
      }
    }
  }
}
