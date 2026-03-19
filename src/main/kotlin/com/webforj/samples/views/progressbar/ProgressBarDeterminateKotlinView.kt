package com.webforj.samples.views.progressbar

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.progressbar.progressBar
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Progress Bar Determinate")
class ProgressBarDeterminateKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
      self.apply {
        maxWidth = 320.px
        styles["margin"] = "0 auto"
        styles["padding"] = 20.px
        progressBar(text = "Loading...") {
          isIndeterminate = true
          isAnimated = true
          isStriped = true
        }
      }
  }
}
