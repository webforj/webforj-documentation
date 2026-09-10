package com.webforj.samples.views.spinner

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.spinner.SpinnerExpanse
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.spinner.spinner
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Spinner Directions")
class SpinnerDirectionDemoKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-l)"
      val spinner = spinner(Theme.PRIMARY, SpinnerExpanse.MEDIUM)
      flexLayout {
        margin = "var(--dwc-space-s)"
        button("Clockwise") {
          width = 200.px
          onClick { spinner.isClockwise = true }
        }
        button("Counterclockwise") {
          width = 200.px
          onClick { spinner.isClockwise = false }
        }
      }
    }
  }
}
