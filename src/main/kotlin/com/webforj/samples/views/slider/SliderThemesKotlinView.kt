package com.webforj.samples.views.slider

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.slider.slider
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Slider Themes")
class SliderThemesKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      margin = "var(--dwc-space-l)"
      arrayOf(
        Theme.DEFAULT,
        Theme.DANGER,
        Theme.GRAY,
        Theme.INFO,
        Theme.SUCCESS,
        Theme.WARNING,
      ).forEach {
        slider(50, 0, 100) {
          majorTickSpacing = 10
          minorTickSpacing = 2
          isTicksVisible = true
          isLabelsVisible = true
          isFilled = true
          theme = it
          width = 500.px
        }
      }
    }
  }
}
