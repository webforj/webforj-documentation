package com.webforj.samples.views.spinner

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.spinner.Spinner
import com.webforj.component.spinner.SpinnerExpanse
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.spinner.spinner
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Spinner Speeds")
class SpinnerSpeedDemoKotlinView: Composite<FlexLayout>() {
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
        button("Slow") {
          onClick { spinner.setSpinnerSpeed(1000) }
        }
        button("Medium") {
          onClick { spinner.setSpinnerSpeed(500) }
        }
        button("Fast") {
          onClick { spinner.setSpinnerSpeed(200) }
        }
        button("Pause") {
          theme = ButtonTheme.PRIMARY
          onClick { spinner.isPaused = true }
        }
      }
    }
  }

  private fun Spinner.setSpinnerSpeed(speed: Int) {
    this.speed = speed
    isPaused = false
  }
}
